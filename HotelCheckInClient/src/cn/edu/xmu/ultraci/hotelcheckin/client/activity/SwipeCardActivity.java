package cn.edu.xmu.ultraci.hotelcheckin.client.activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import cn.edu.xmu.ultraci.hotelcheckin.client.R;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.Broadcast;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.TTS;
import cn.edu.xmu.ultraci.hotelcheckin.client.util.StringUtil;
import cn.edu.xmu.ultraci.hotelcheckin.client.util.SystemUtil;

/**
 * 刷卡界面
 *
 */
public class SwipeCardActivity extends BaseActivity {

	private SwipeCardReceiver receiver;

	private NfcAdapter mAdapter;
	private PendingIntent mPendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;

	// 记录卡验证通过后去往哪个界面
	private String fromActivity;
	private String nextActivity;
	private String cardid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView(true, getTitle().toString(), true, 20, R.layout.activity_swipe_card, false);
		registerReceiver();

		bindCoreService();
		bindMiscService();
		bindThirdpartyService();

		fromActivity = getIntent().getStringExtra("from");
		nextActivity = getIntent().getStringExtra("next");

		mAdapter = NfcAdapter.getDefaultAdapter(this);
		mPendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		mFilters = new IntentFilter[] { new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED) };
		mTechLists = new String[][] { new String[] { NfcA.class.getName() } };

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mAdapter != null) {
			mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mAdapter != null) {
			mAdapter.disableForegroundDispatch(this);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SystemUtil.unregisterLocalBroadcast(this, receiver);
	}

	@Override
	public void onNewIntent(Intent intent) {
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		cardid = StringUtil.byte2HexString(tagFromIntent.getId());
		// 播放刷卡音效
		getMiscServiceBinder().playEffect(R.raw.ding);
		// 处理读取到的卡ID
		if (nextActivity.equals(VoiceprintActivity.class.getSimpleName())) {
			if (fromActivity.equals(InitActivity.class.getSimpleName())) {
				// 登录
				getCoreServiceBinder().login(cardid);
			} else if (fromActivity.equals(MainActivity.class.getSimpleName())) {
				// 登出
				getCoreServiceBinder().logout(cardid);
			}
		} else if (nextActivity.equals(SelectTimeActivity.class.getSimpleName())) {
			// 会员入住
			getCoreServiceBinder().member(cardid);
		} else if (nextActivity.equals(RoomInfoActivity.class.getSimpleName())) {
			// 查看房间信息
			getCoreServiceBinder().room(cardid);
		}
	}

	private void registerReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Broadcast.THIRDPARTY_SERIVCE_BOUND);
		filter.addAction(Broadcast.CORE_SERIVCE_BOUND);
		filter.addAction(Broadcast.CORE_LOGIN_OK);
		filter.addAction(Broadcast.CORE_LOGOUT_OK);
		filter.addAction(Broadcast.CORE_LOGIN_NO_PREMISSION);
		filter.addAction(Broadcast.CORE_LOGOUT_NO_PREMISSION);
		filter.addAction(Broadcast.CORE_LOGIN_NO_SUCH_CARD);
		filter.addAction(Broadcast.CORE_LOGOUT_NO_SUCH_CARD);
		filter.addAction(Broadcast.CORE_QUERY_MEMBER_OK);
		filter.addAction(Broadcast.CORE_QUERY_MEMBER_NO_SUCH_CARD);
		filter.addAction(Broadcast.CORE_QUERY_ROOM_OK);
		filter.addAction(Broadcast.CORE_QUERY_ROOM_NO_CHECKIN);
		filter.addAction(Broadcast.CORE_QUERY_ROOM_NO_SUCH_CARD);
		receiver = new SwipeCardReceiver();
		SystemUtil.registerLocalBroadcast(this, receiver, filter);
	}

	class SwipeCardReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Intent newIntent;
			switch (intent.getAction()) {
			case Broadcast.THIRDPARTY_SERIVCE_BOUND:
				// 合成语音
				if (nextActivity.equals(VoiceprintActivity.class.getSimpleName())) {
					getThirdpartyServiceBinder().synthesicSpeech(TTS.SWIPE_CRAD_LOGIN_OUT);
				} else if (nextActivity.equals(SelectTimeActivity.class.getSimpleName())) {
					getThirdpartyServiceBinder().synthesicSpeech(TTS.SWIPE_CRAD_QUERY_MEMBER);
				} else if (nextActivity.equals(RoomInfoActivity.class.getSimpleName())) {
					getThirdpartyServiceBinder().synthesicSpeech(TTS.SWIPE_CRAD_QUERY_ROOM);
				}
				break;
			case Broadcast.CORE_SERIVCE_BOUND:
				// 请求服务端
				break;
			case Broadcast.CORE_LOGIN_OK:
				// 登陆成功
				finish();
				newIntent = new Intent(SwipeCardActivity.this, VoiceprintActivity.class);
				newIntent.putExtra("from", SwipeCardActivity.class.getSimpleName());
				newIntent.putExtra("next", MainActivity.class.getSimpleName());
				newIntent.putExtra("uid", "u" + cardid);
				startActivity(newIntent);
				break;
			case Broadcast.CORE_LOGOUT_OK:
				// 登出成功
				finish();
				newIntent = new Intent(SwipeCardActivity.this, VoiceprintActivity.class);
				newIntent.putExtra("from", SwipeCardActivity.class.getSimpleName());
				newIntent.putExtra("next", MainActivity.class.getSimpleName());
				newIntent.putExtra("uid", "u" + cardid);
				startActivity(newIntent);
				break;
			case Broadcast.CORE_LOGIN_NO_PREMISSION:
				// 登录失败无权限
				getThirdpartyServiceBinder().synthesicSpeech(TTS.SWIPE_CRAD_LOGIN_NO_PREMISSION);
				break;
			case Broadcast.CORE_LOGOUT_NO_PREMISSION:
				// 登出失败无权限
				getThirdpartyServiceBinder().synthesicSpeech(TTS.SWIPE_CRAD_LOGOUT_NO_PREMISSION);
				break;
			case Broadcast.CORE_LOGIN_NO_SUCH_CARD:
			case Broadcast.CORE_LOGOUT_NO_SUCH_CARD:
				// 登陆(登出)失败无此卡
				getThirdpartyServiceBinder().synthesicSpeech(TTS.SWIPE_CRAD_LOGIN_OUT_NO_SUCH_CARD);
				break;
			case Broadcast.CORE_QUERY_MEMBER_OK:
				// 查询会员成功
				finish();
				newIntent = new Intent(SwipeCardActivity.this, SelectTimeActivity.class);
				newIntent.putExtra("retModel", intent.getSerializableExtra("retModel"));
				startActivity(newIntent);
				break;
			case Broadcast.CORE_QUERY_MEMBER_NO_SUCH_CARD:
				// 查询会员失败无此卡
				getThirdpartyServiceBinder().synthesicSpeech(TTS.SWIPE_CRAD_QUERY_MEMBER_NO_SUCH_CARD);
				break;
			case Broadcast.CORE_QUERY_ROOM_OK:
				// 查询房间成功
				finish();
				newIntent = new Intent(SwipeCardActivity.this, RoomInfoActivity.class);
				newIntent.putExtra("retModel", intent.getSerializableExtra("retModel"));
				startActivity(newIntent);
				break;
			case Broadcast.CORE_QUERY_ROOM_NO_CHECKIN:
				// 查询房间失败未入住
				getThirdpartyServiceBinder().synthesicSpeech(TTS.SWIPE_CRAD_QUERY_ROOM_NO_CHECKIN);
				break;
			case Broadcast.CORE_QUERY_ROOM_NO_SUCH_CARD:
				// 查询房间失败无此卡
				getThirdpartyServiceBinder().synthesicSpeech(TTS.SWIPE_CRAD_QUERY_ROOM_NO_SUCH_CARD);
				break;
			}
		}
	}
}
