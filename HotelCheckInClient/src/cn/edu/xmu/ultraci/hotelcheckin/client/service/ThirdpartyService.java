package cn.edu.xmu.ultraci.hotelcheckin.client.service;

import com.alibaba.fastjson.JSONObject;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeakerVerifier;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.VerifierListener;
import com.iflytek.cloud.VerifierResult;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.Broadcast;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.client.util.StringUtil;
import cn.edu.xmu.ultraci.hotelcheckin.client.util.SystemUtil;
import cn.edu.xmu.ultraci.hotelcheckin.client.util.TimeUtil;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 第三方服务<br>
 * <ul>
 * 科大讯飞语音合成<br>
 * 科大讯飞声纹密码<br>
 * 掌淘科技验证码<br>
 * 云片网络验证码<br>
 * </ul>
 * 
 * @author LuoXin
 *
 */
public class ThirdpartyService extends Service {
	private static final String TAG = ThirdpartyService.class.getSimpleName();

	private InitListener mInitListener;
	private SpeechSynthesizer mSynthesizer;
	private SynthesizerListener mSynthesizerListener;
	private SpeakerVerifier mVerifier;
	private VerifierListener mVerifierListener;

	@Override
	public void onCreate() {
		super.onCreate();

		initXFSdk();
		initZTSdk();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 销毁讯飞SDK实例
		if (mSynthesizer != null) {
			if (mSynthesizer.isSpeaking()) {
				mSynthesizer.stopSpeaking();
			}
			mSynthesizer.destroy();
		}
		if (mVerifier != null) {
			if (mVerifier.isListening()) {
				mVerifier.stopListening();
			}
			mVerifier.destroy();
		}
		// 解除掌淘SDK事件监听器
		SMSSDK.unregisterAllEventHandler();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return new ThirdpartyServiceBinder();
	}

	/**
	 * 初始化科大讯飞SDK
	 */
	private void initXFSdk() {
		// SDK初始化监听器
		mInitListener = new InitListener() {
			@Override
			public void onInit(int errorCode) {
				if (errorCode != ErrorCode.SUCCESS) {
					Log.e(TAG, String.format(LogTemplate.IFLYTEK_INIT_FAIL, errorCode));
					SystemUtil.sendLocalBroadcast(ThirdpartyService.this, new Intent(Broadcast.IFLYTEK_INIT_FAIL));
				} else {
					Log.i(TAG, LogTemplate.IFLYTEK_INIT_OK);
				}
			}
		};
		// 语音合成监听器
		mSynthesizerListener = new SynthesizerListener() {
			@Override
			public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
			}

			@Override
			public void onCompleted(SpeechError arg0) {
				if (arg0 != null && arg0.getErrorCode() != ErrorCode.SUCCESS) {
					Log.e(TAG, String.format(LogTemplate.IFLYTEK_SYNTHESIS_FAIL, arg0.getErrorCode()));
				} else {
					Log.i(TAG, LogTemplate.IFLYTEK_SYNTHESIS_OK);
					SystemUtil.sendLocalBroadcast(ThirdpartyService.this, new Intent(Broadcast.IFLYTEK_SYNTHESIS_OK));
				}
			}

			@Override
			public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			}

			@Override
			public void onSpeakBegin() {
			}

			@Override
			public void onSpeakPaused() {
			}

			@Override
			public void onSpeakProgress(int arg0, int arg1, int arg2) {
			}

			@Override
			public void onSpeakResumed() {
			}
		};
		// 声纹验证监听器
		mVerifierListener = new VerifierListener() {
			@Override
			public void onVolumeChanged(int arg0, byte[] arg1) {
				// 更新音量电平指示器
				Intent intent = new Intent(Broadcast.IFLYTEK_RECORD_VOLUME_CHANGE);
				intent.putExtra("volume", arg0);
				SystemUtil.sendLocalBroadcast(ThirdpartyService.this, intent);
			}

			@Override
			public void onResult(VerifierResult arg0) {
				// 处理声纹密码验证结果
				if (arg0.ret == ErrorCode.SUCCESS) {
					Log.i(TAG, String.format(LogTemplate.IFLYTEK_VERIFY_OK, arg0.vid));
					SystemUtil.sendLocalBroadcast(ThirdpartyService.this, new Intent(Broadcast.IFLYTEK_VERIFY_OK));
				} else {
					Log.e(TAG, String.format(LogTemplate.IFLYTEK_VERIFY_FAIL, arg0.err));
					SystemUtil.sendLocalBroadcast(ThirdpartyService.this,
							new Intent(Broadcast.IFLYTEK_VERIFY_FAIL_OTHER));
				}
			}

			@Override
			public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			}

			@Override
			public void onError(SpeechError arg0) {
				if (arg0 != null && arg0.getErrorCode() != ErrorCode.SUCCESS) {
					Log.e(TAG, String.format(LogTemplate.IFLYTEK_VERIFY_FAIL, arg0.getErrorCode()));
					switch (arg0.getErrorCode()) {
					// 太多噪音、声音太小、没检测到音频
					case VerifierResult.MSS_ERROR_IVP_MUCH_NOISE:
					case VerifierResult.MSS_ERROR_IVP_TOO_LOW:
					case VerifierResult.MSS_ERROR_IVP_ZERO_AUDIO:
						SystemUtil.sendLocalBroadcast(ThirdpartyService.this,
								new Intent(Broadcast.IFLYTEK_VERIFY_FAIL_VOICE));
						break;
					// 音频内容与给定文本不一致
					case VerifierResult.MSS_ERROR_IVP_TEXT_NOT_MATCH:
						SystemUtil.sendLocalBroadcast(ThirdpartyService.this,
								new Intent(Broadcast.IFLYTEK_VERIFY_FAIL_TEXT));
						break;
					// 其他错误
					default:
						SystemUtil.sendLocalBroadcast(ThirdpartyService.this,
								new Intent(Broadcast.IFLYTEK_VERIFY_FAIL_OTHER));
						break;
					}
				}
			}

			@Override
			public void onEndOfSpeech() {
				// 隐藏音量电平指示器
				SystemUtil.sendLocalBroadcast(ThirdpartyService.this, new Intent(Broadcast.IFLYTEK_RECORD_END));
			}

			@Override
			public void onBeginOfSpeech() {
				// 显示音量电平指示器
				SystemUtil.sendLocalBroadcast(ThirdpartyService.this, new Intent(Broadcast.IFLYTEK_RECORD_START));
			}
		};
		// 创建实例
		mSynthesizer = SpeechSynthesizer.createSynthesizer(this, mInitListener);
		mVerifier = SpeakerVerifier.createVerifier(this, mInitListener);
	}

	/**
	 * 初始化掌淘科技SDK
	 */
	private void initZTSdk() {
		SMSSDK.registerEventHandler(new EventHandler() {
			@Override
			public void afterEvent(int arg0, int arg1, Object arg2) {
				if (arg1 == SMSSDK.RESULT_COMPLETE) {
					switch (arg0) {
					case SMSSDK.EVENT_GET_CONTACTS:
						break;
					case SMSSDK.EVENT_GET_FRIENDS_IN_APP:
						break;
					case SMSSDK.EVENT_GET_NEW_FRIENDS_COUNT:
						break;
					case SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES:
						break;
					case SMSSDK.EVENT_GET_VERIFICATION_CODE:
						// 请求短信验证码
						SystemUtil.sendLocalBroadcast(ThirdpartyService.this,
								new Intent(Broadcast.MOB_CAPTCHA_SMS_SEND));
						Log.i(TAG, LogTemplate.MOB_CAPTCHA_SMS_SEND);
						break;
					case SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE:
						// 请求语音验证码
						SystemUtil.sendLocalBroadcast(ThirdpartyService.this,
								new Intent(Broadcast.MOB_CAPTCHA_VOICE_SEND));
						Log.i(TAG, LogTemplate.MOB_CAPTCHA_VOICE_SEND);
						break;
					case SMSSDK.EVENT_SUBMIT_USER_INFO:
						break;
					case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
						// 提交验证码
						SystemUtil.sendLocalBroadcast(ThirdpartyService.this,
								new Intent(Broadcast.MOB_CAPTCHA_VERIFY_OK));
						Log.i(TAG, LogTemplate.MOB_CAPTCHA_VERIFY_OK);
						break;
					}
				} else {
					JSONObject err = JSONObject.parseObject(arg2.toString());
					if (err.containsKey("status") && err.getInteger("status") == 468) {
						// 官方无文档，暂时只处理验证码错误468
						SystemUtil.sendLocalBroadcast(ThirdpartyService.this,
								new Intent(Broadcast.MOB_CAPTCHA_VERIFY_FAIL));
						Log.w(TAG, LogTemplate.MOB_CAPTCHA_VERIFY_FAIL);
					} else {
						Log.e(TAG, String.format(LogTemplate.MOB_CAPTCHA_UNKNOWN_FAIL, err.getInteger("status")));
					}
				}
			}
		});
	}

	/**
	 * 设置语音合成参数
	 */
	private void setSynthesisParams() {
		// 设置引擎类型
		mSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		// 设置合成发音人
		mSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoqi");
		// 设置合成语速
		mSynthesizer.setParameter(SpeechConstant.SPEED, "50");
		// 设置合成音调
		mSynthesizer.setParameter(SpeechConstant.PITCH, "50");
		// 设置合成音量
		mSynthesizer.setParameter(SpeechConstant.VOLUME, "100");
		// 设置播放器音频流类型
		mSynthesizer.setParameter(SpeechConstant.STREAM_TYPE, AudioManager.STREAM_MUSIC + "");
		// 设置播放合成音频打断音乐播放
		mSynthesizer.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
		// 设置音频保存路径
		mSynthesizer.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
		mSynthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH,
				getCacheDir() + "/iflytek/tts/" + TimeUtil.getCurrentTime() + ".pcm");
	}

	/**
	 * 设置声纹验证参数
	 * 
	 * @param uid
	 *            用户ID
	 * @param pwd
	 *            声纹密码
	 */
	private void setVerifierParams(String uid, String pwd) {
		// 设置声纹业务类型
		mVerifier.setParameter(SpeechConstant.ISV_SST, "verify");
		// 设置声纹密码类型
		mVerifier.setParameter(SpeechConstant.ISV_PWDT, "3");
		// 设置用户唯一标识
		mVerifier.setParameter(SpeechConstant.AUTH_ID, uid);
		// 设置验证使用的声纹密码
		mVerifier.setParameter(SpeechConstant.ISV_PWD, pwd);
		// 设置声纹录音保存路径
		mVerifier.setParameter(SpeechConstant.ISV_AUDIO_PATH,
				getCacheDir() + "/iflytek/isv/" + TimeUtil.getCurrentTime() + ".pcm");
		// mVerifier.setParameter(SpeechConstant.AUDIO_SOURCE,
		// MediaRecorder.AudioSource.VOICE_RECOGNITION + "");
	}

	/**
	 * 生成声纹密码
	 * 
	 * @return 声纹密码
	 */
	public String getVoiceprintPassword() {
		return mVerifier.generatePassword(8);
	}

	/**
	 * 验证声纹
	 * 
	 * @param uid
	 *            用户ID
	 * @param pwd
	 *            声纹密码
	 */
	public void verifyVoiceprint(String uid, String pwd) {
		if (StringUtil.isUsername(uid) && StringUtil.isNumeric(pwd)) {
			setVerifierParams(uid, pwd);
			if (mVerifier.isListening()) {
				mVerifier.stopListening();
			}
			mVerifier.startListening(mVerifierListener);
		}
	}

	/**
	 * 合成语音
	 * 
	 * @param text
	 *            合成文本
	 */
	public void syntheticSpeech(String text) {
		if (!StringUtil.isBlank(text)) {
			setSynthesisParams();
			if (mSynthesizer.isSpeaking()) {
				mSynthesizer.stopSpeaking();
			}
			mSynthesizer.startSpeaking(text, mSynthesizerListener);
		}
	}

	/**
	 * 发送短信验证码(云片网络)
	 * 
	 * @param mobile
	 *            手机号
	 * @return 验证码
	 */
	public String sendSMSCaptchaV2(String mobile) {
		// TODO
		return null;
	}

	/**
	 * 发送短信验证码(掌淘科技)
	 * 
	 * @param mobile
	 *            手机号
	 */
	public void sendSMSCaptcha(String mobile) {
		SMSSDK.getVerificationCode("86", mobile);
	}

	/**
	 * 发送语音验证码(掌淘科技)
	 * 
	 * @param mobile
	 *            手机号
	 */
	public void sendVoiceCaptcha(String mobile) {
		SMSSDK.getVoiceVerifyCode("86", mobile);
	}

	/**
	 * 校验验证码(掌淘科技)
	 * 
	 * @param mobile
	 *            手机号
	 * @param captcha
	 *            验证码
	 */
	public void verifyCaptcha(String mobile, String captcha) {
		SMSSDK.submitVerificationCode("86", mobile, captcha);
	}

	public class ThirdpartyServiceBinder extends Binder {
		public String getVoiceprintPassword() {
			return ThirdpartyService.this.getVoiceprintPassword();
		}

		public void verifyVoiceprint(String uid, String pwd) {
			ThirdpartyService.this.verifyVoiceprint(uid, pwd);
		}

		public void synthesicSpeech(String text) {
			ThirdpartyService.this.syntheticSpeech(text);
		}

		public void sendSMSCaptchaV2(String mobile) {
			ThirdpartyService.this.sendSMSCaptchaV2(mobile);
		}

		public void sendSMSCaptcha(String mobile) {
			ThirdpartyService.this.sendSMSCaptcha(mobile);
		}

		public void sendVoiceCaptcha(String mobile) {
			ThirdpartyService.this.sendVoiceCaptcha(mobile);
		}

		public void verifyCaptcha(String mobile, String captcha) {
			ThirdpartyService.this.verifyCaptcha(mobile, captcha);
		}
	}

}
