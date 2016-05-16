package cn.edu.xmu.ultraci.hotelcheckin.client.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.apache.commons.codec.android.digest.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.Action;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.Broadcast;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.Config;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.ErrorCode;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.URL;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.CheckinDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.CheckoutDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.FileUploadDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.FloorDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.GuestDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.HeartbeatDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.InitDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.LoginDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.LogoutDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.MemberDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.RoomDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.StatusDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.dto.TypeDTO;
import cn.edu.xmu.ultraci.hotelcheckin.client.util.HttpUtil;
import cn.edu.xmu.ultraci.hotelcheckin.client.util.RandomUtil;
import cn.edu.xmu.ultraci.hotelcheckin.client.util.SystemUtil;
import cz.msebera.android.httpclient.Header;

/**
 * 核心业务逻辑实现<br>
 * 主要用于同服务端交互
 * 
 * @author LuoXin
 *
 */
public class CoreService extends Service {
	private static final String TAG = CoreService.class.getSimpleName();

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new CoreServiceBinder();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 根据文档5.1节所述算法计算加密签名
	 * 
	 * @param random
	 *            随机字符串
	 * @return 加密签名
	 */
	private String getSignature(String random) {
		// 排序和合并
		String[] array = new String[] { Config.TOKEN, random };
		Arrays.sort(array);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
		}
		// 计算加密签名
		return DigestUtils.sha1Hex(sb.toString());
	}

	/**
	 * 向HTTP请求中添加共通的参数<br>
	 * 包括：random、signature、action、device
	 * 
	 * @param params
	 *            请求参数集
	 * @param action
	 *            请求类型
	 */
	private void addCommonParams(RequestParams params, String action) {
		String random = RandomUtil.generateRandomStr(6);
		params.put("random", random);
		params.put("signature", getSignature(random));
		params.put("device", SystemUtil.getMacAddress(this));
		// 文件上传接口目前不需要action参数
		if (action != null) {
			params.put("action", action);
		}
	}

	/**
	 * 当请求服务端出错时调用
	 * 
	 * @param errorCode
	 *            服务端错误代码
	 */
	private void onServerFailure(int errorCode) {
		// 服务端程序错误代码为-1或大于30000的值
		// 而HTTP状态码不会超过600
		if (errorCode == -1 || errorCode > 10000) {
			Log.e(TAG, String.format(LogTemplate.CORE_SERVER_PROCESS_FAIL, errorCode));
			SystemUtil.sendLocalBroadcast(this, new Intent(Broadcast.CORE_SERVER_PROCESS_FAIL));
		} else {
			Log.e(TAG, String.format(LogTemplate.CORE_SERVER_REQUEST_FAIL, errorCode));
			SystemUtil.sendLocalBroadcast(this, new Intent(Broadcast.CORE_SERVER_REQUEST_FAIL));
		}
	}

	/**
	 * 心跳
	 */
	public void heartbeat() {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.HEARTBEAT);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				HeartbeatDTO retModel = JSON.parseObject(new String(arg2), HeartbeatDTO.class);
				if (retModel.getResult() != ErrorCode.OK) {
					// 心跳响应异常视为服务端错误
					// 表现层应区别处理
					onServerFailure(retModel.getResult());
				}
			}
		});
	}

	/**
	 * 初始化
	 */
	public void init() {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.INIT);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				InitDTO retModel = JSON.parseObject(new String(arg2), InitDTO.class);
				if (retModel.getResult() == ErrorCode.OK) {
					if (retModel.getUpgrade() != null) {
						// TODO 客户端在线升级
					}
					if (retModel.getAds().size() != 0) {
						// TODO 客户端闲时广告
					}
					Intent intent = new Intent(Broadcast.CORE_INIT_OK);
					intent.putExtra("notice", retModel.getNotice());
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
				} else {
					onServerFailure(retModel.getResult());
				}
			}
		});
	}

	/**
	 * 登录
	 * 
	 * @param cardid
	 *            员工卡号
	 */
	public void login(String cardid) {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.LOGIN);
		params.put("cardid", cardid);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				LoginDTO retModel = JSON.parseObject(new String(arg2), LoginDTO.class);
				Intent intent;
				switch (retModel.getResult()) {
				case ErrorCode.OK:
					Log.i(TAG, String.format(LogTemplate.CORE_LOGIN_OK, retModel.getId()));
					intent = new Intent(Broadcast.CORE_LOGIN_OK);
					intent.putExtra("no", retModel.getNo());
					intent.putExtra("name", retModel.getName());
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
					break;
				case ErrorCode.LOGIN_OUT_NO_PREMISSION:
					Log.w(TAG, LogTemplate.CORE_LOGIN_NO_PREMISSION);
					intent = new Intent(Broadcast.CORE_LOGIN_NO_PREMISSION);
					intent.putExtra("name", retModel.getName());
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
					break;
				case ErrorCode.LOGIN_OUT_NO_SUCH_CARD:
					Log.w(TAG, LogTemplate.CORE_LOGIN_NO_SUCH_CARD);
					intent = new Intent(Broadcast.CORE_LOGIN_NO_SUCH_CARD);
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
					break;
				default:
					onServerFailure(retModel.getResult());
					break;
				}
			}
		});
	}

	/**
	 * 登出
	 * 
	 * @param cardid
	 *            员工卡号
	 */
	public void logout(String cardid) {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.LOGOUT);
		params.put("cardid", cardid);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				LogoutDTO retModel = JSON.parseObject(new String(arg2), LogoutDTO.class);
				Intent intent;
				switch (retModel.getResult()) {
				case ErrorCode.OK:
					Log.i(TAG, String.format(LogTemplate.CORE_LOGOUT_OK, retModel.getId()));
					intent = new Intent(Broadcast.CORE_LOGOUT_OK);
					intent.putExtra("name", retModel.getName());
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
					break;
				case ErrorCode.LOGIN_OUT_NO_PREMISSION:
					Log.w(TAG, LogTemplate.CORE_LOGOUT_NO_PREMISSION);
					intent = new Intent(Broadcast.CORE_LOGOUT_NO_PREMISSION);
					intent.putExtra("no", retModel.getNo());
					intent.putExtra("name", retModel.getName());
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
					break;
				case ErrorCode.LOGIN_OUT_NO_SUCH_CARD:
					Log.w(TAG, LogTemplate.CORE_LOGOUT_NO_SUCH_CARD);
					intent = new Intent(Broadcast.CORE_LOGOUT_NO_SUCH_CARD);
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
					break;
				default:
					onServerFailure(retModel.getResult());
					break;
				}
			}
		});
	}

	/**
	 * 查询会员
	 * 
	 * @param cardid
	 *            会员卡号
	 */
	public void member(String cardid) {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.QUERY_MEMBER);
		params.put("cardid", cardid);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				MemberDTO retModel = JSON.parseObject(new String(arg2), MemberDTO.class);
				switch (retModel.getResult()) {
				case ErrorCode.OK:
					Log.i(TAG, String.format(LogTemplate.CORE_QUERY_MEMBER_OK, retModel.getId()));
					// 查询类服务返回模型较复杂，直接转发到表现层，下同
					Intent intent = new Intent(Broadcast.CORE_QUERY_MEMBER_OK);
					intent.putExtra("retModel", retModel);
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
					break;
				case ErrorCode.QUERY_MEMBER_NO_SUCH_CARD:
					Log.w(TAG, LogTemplate.CORE_QUERY_MEMBER_NO_SUCH_CARD);
					SystemUtil.sendLocalBroadcast(CoreService.this,
							new Intent(Broadcast.CORE_QUERY_MEMBER_NO_SUCH_CARD));
					break;
				default:
					onServerFailure(retModel.getResult());
					break;
				}
			}
		});
	}

	/**
	 * 查询房型
	 */
	public void type() {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.QUERY_TYPE);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				TypeDTO retModel = JSON.parseObject(new String(arg2), TypeDTO.class);
				if (retModel.getResult() == ErrorCode.OK) {
					Log.i(TAG, String.format(LogTemplate.CORE_QUERY_TYPE_OK, retModel.getTypes().size()));
					Intent intent = new Intent(Broadcast.CORE_QUERY_TYPE_OK);
					intent.putExtra("retModel", retModel);
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
				} else {
					onServerFailure(retModel.getResult());
				}
			}
		});
	}

	/**
	 * 查询楼层
	 */
	public void floor() {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.QUERY_FLOOR);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				FloorDTO retModel = JSON.parseObject(new String(arg2), FloorDTO.class);
				if (retModel.getResult() == ErrorCode.OK) {
					Log.i(TAG, String.format(LogTemplate.CORE_QUERY_FLOOR_OK, retModel.getFloors().size()));
					Intent intent = new Intent(Broadcast.CORE_QUERY_FLOOR_OK);
					intent.putExtra("retModel", retModel);
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
				} else {
					onServerFailure(retModel.getResult());
				}
			}
		});
	}

	/**
	 * 查询房态
	 * 
	 * @param floor
	 *            楼层筛选
	 * @param type
	 *            房型筛选
	 */
	public void status(String floor, String type) {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.QUERY_STATUS);
		params.put("floor", floor);
		params.put("type", type);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				StatusDTO retModel = JSON.parseObject(new String(arg2), StatusDTO.class);
				if (retModel.getResult() == ErrorCode.OK) {
					Log.i(TAG, String.format(LogTemplate.CORE_QUERY_STATUS_OK, retModel.getStatuses().size()));
					Intent intent = new Intent(Broadcast.CORE_QUERY_STATUS_OK);
					intent.putExtra("retModel", retModel);
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
				} else {
					onServerFailure(retModel.getResult());
				}
			}
		});
	}

	/**
	 * 查询房间
	 * 
	 * @param cardid
	 *            房卡号
	 */
	public void room(String cardid) {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.QUERY_ROOM);
		params.put("cardid", cardid);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				RoomDTO retModel = JSON.parseObject(new String(arg2), RoomDTO.class);
				switch (retModel.getResult()) {
				case ErrorCode.OK:
					Log.i(TAG, String.format(LogTemplate.CORE_QUERY_ROOM_OK, retModel.getId()));
					Intent intent = new Intent(Broadcast.CORE_QUERY_ROOM_OK);
					intent.putExtra("retModel", retModel);
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
					break;
				case ErrorCode.QUERY_ROOM_NO_CHECK_IN:
					Log.w(TAG, String.format(LogTemplate.CORE_QUERY_ROOM_NO_CHECKIN, retModel.getId()));
					SystemUtil.sendLocalBroadcast(CoreService.this, new Intent(Broadcast.CORE_QUERY_ROOM_NO_CHECKIN));
					break;
				case ErrorCode.QUERY_ROOM_NO_SUCH_CARD:
					Log.w(TAG, LogTemplate.CORE_QUERY_ROOM_NO_SUCH_CARD);
					SystemUtil.sendLocalBroadcast(CoreService.this, new Intent(Broadcast.CORE_QUERY_ROOM_NO_SUCH_CARD));
					break;
				default:
					onServerFailure(retModel.getResult());
					break;
				}
			}
		});
	}

	/**
	 * 提交散客信息
	 * 
	 * @param mobile
	 *            手机号
	 * @param idcard
	 *            身份证(图片文件名)
	 */
	public void guest(final String mobile, String idcard) {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.NEW_GUEST);
		params.put("mobile", mobile);
		params.put("idcard", idcard);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				GuestDTO retModel = JSON.parseObject(new String(arg2), GuestDTO.class);
				if (retModel.getResult() == ErrorCode.OK) {
					Log.i(TAG, String.format(LogTemplate.CORE_GUEST_OK, retModel.getId()));
					Intent intent = new Intent(Broadcast.CORE_GUEST_OK);
					intent.putExtra("id", retModel.getId());
					SystemUtil.sendLocalBroadcast(CoreService.this, intent);
				} else {
					onServerFailure(retModel.getResult());
				}
			}
		});
	}

	/**
	 * 办理入住手续
	 * 
	 * @param customer
	 *            顾客ID(散客或会员)
	 * @param room
	 *            房间ID
	 * @param time
	 *            拟退房时间
	 */
	public void checkin(String customer, String room, String time) {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.CHECKIN);
		params.put("customer", customer);
		params.put("room", room);
		params.put("time", time);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				CheckinDTO retModel = JSON.parseObject(new String(arg2), CheckinDTO.class);
				if (retModel.getResult() == ErrorCode.OK) {
					Log.i(TAG, LogTemplate.CORE_CHECKIN_OK);
					SystemUtil.sendLocalBroadcast(CoreService.this, new Intent(Broadcast.CORE_CHECKIN_OK));
				} else {
					onServerFailure(retModel.getResult());
				}
			}
		});
	}

	/**
	 * 办理退房手续
	 * 
	 * @param cardid
	 *            房卡号
	 */
	public void checkout(String cardid) {
		RequestParams params = new RequestParams();
		addCommonParams(params, Action.CHECKOUT);
		params.put("cardid", cardid);
		HttpUtil.post(URL.CLIENT_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				CheckoutDTO retModel = JSON.parseObject(new String(arg2), CheckoutDTO.class);
				switch (retModel.getResult()) {
				case ErrorCode.OK:
					Log.i(TAG, LogTemplate.CORE_CHECKOUT_OK);
					SystemUtil.sendLocalBroadcast(CoreService.this, new Intent(Broadcast.CORE_CHECKOUT_OK));
					break;
				case ErrorCode.CHECKOUT_NEED_PAY:
					Log.w(TAG, LogTemplate.CORE_CHECKOUT_NEED_PAY);
					SystemUtil.sendLocalBroadcast(CoreService.this, new Intent(Broadcast.CORE_CHECKOUT_NEED_PAY));
					break;
				case ErrorCode.CHECKOUT_NO_CHECKIN:
					Log.w(TAG, LogTemplate.CORE_CHECKOUT_NO_CHECKIN);
					SystemUtil.sendLocalBroadcast(CoreService.this, new Intent(Broadcast.CORE_CHECKOUT_NO_CHECKIN));
					break;
				case ErrorCode.CHECKOUT_NO_SUCH_CARD:
					Log.w(TAG, LogTemplate.CORE_CHECKOUT_NO_SUCH_CARD);
					SystemUtil.sendLocalBroadcast(CoreService.this, new Intent(Broadcast.CORE_CHECKOUT_NO_SUCH_CARD));
					break;
				default:
					onServerFailure(retModel.getResult());
					break;
				}
			}
		});
	}

	/**
	 * 文件上传
	 * 
	 * @param type
	 *            上传类型
	 * @param filename
	 *            文件名
	 */
	public void upload(String type, String filename) {
		RequestParams params = new RequestParams();
		addCommonParams(params, null);
		params.put("type", type);
		try {
			params.put("file", new File(filename));
		} catch (FileNotFoundException e) {
			Log.i(TAG, String.format(LogTemplate.CORE_FILE_UPLOAD_FAIL, e.getMessage()));
			onServerFailure(ErrorCode.FILE_UPLOAD_ERROR);
			return;
		}
		HttpUtil.post(URL.FILE_UPLOAD_URL, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				onServerFailure(arg0);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, new String(arg2));
				FileUploadDTO retModel = JSON.parseObject(new String(arg2), FileUploadDTO.class);
				if (retModel.getResult() == ErrorCode.OK) {
					Log.i(TAG, String.format(LogTemplate.CORE_FILE_UPLOAD_OK, retModel.getFilename()));
					SystemUtil.sendLocalBroadcast(CoreService.this, new Intent(Broadcast.CORE_FILE_UPLOAD_OK));
				} else {
					onServerFailure(retModel.getResult());
				}
			}
		});
	}

	/**
	 * 文件下载
	 * 
	 * @param url
	 *            下载地址
	 * @param md5
	 *            文件校验
	 */
	public void download(String url, String md5) {
		HttpUtil.get(url, null, new FileAsyncHttpResponseHandler(this) {
			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2, File arg3) {
				Log.e(TAG, String.format(LogTemplate.CORE_FILE_DOWNLOAD_FAIL, arg2 != null ? arg2.getMessage() : null));
				SystemUtil.sendLocalBroadcast(CoreService.this, new Intent(Broadcast.CORE_FILE_DOWNLOAD_FAIL));
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, File arg2) {
				Log.i(TAG, String.format(LogTemplate.CORE_FILE_DOWNLOAD_OK, arg2.getName()));
				SystemUtil.sendLocalBroadcast(CoreService.this, new Intent(Broadcast.CORE_FILE_DOWNLOAD_OK));
				// TODO 保存文件和MD5校验
			}
		});
	}

	public class CoreServiceBinder extends Binder {
		public void heartbeat() {
			CoreService.this.heartbeat();
		}

		public void init() {
			CoreService.this.init();
		}

		public void login(String cardid) {
			CoreService.this.login(cardid);
		}

		public void logout(String cardid) {
			CoreService.this.logout(cardid);
		}

		public void member(String cardid) {
			CoreService.this.member(cardid);
		}

		public void type() {
			CoreService.this.type();
		}

		public void floor() {
			CoreService.this.floor();
		}

		public void status(String floor, String type) {
			CoreService.this.status(floor, type);
		}

		public void room(String cardid) {
			CoreService.this.room(cardid);
		}

		public void guest(String mobile, String idcard) {
			CoreService.this.guest(mobile, idcard);
		}

		public void checkin(String customer, String room, String time) {
			CoreService.this.checkin(customer, room, time);
		}

		public void checkout(String cardid) {
			CoreService.this.checkout(cardid);
		}

		public void upload(String type, String filename) {
			CoreService.this.upload(type, filename);
		}

		public void download(String url, String md5) {
			CoreService.this.download(url, md5);
		}
	}

}
