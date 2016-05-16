package cn.edu.xmu.ultraci.hotelcheckin.client.test;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;
import cn.edu.xmu.ultraci.hotelcheckin.client.R;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.Broadcast;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.ThirdpartyService;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.ThirdpartyService.ThirdpartyServiceBinder;

public class TestThirdPartyServiceActivity extends Activity {

	private TextView tv;
	private StringBuffer sb = new StringBuffer();
	private ServiceConnection conn;
	private ThirdpartyServiceBinder binder;
	private BroadcastReceiver receiver;
	private String pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_third_party_service);
		tv = (TextView) findViewById(R.id.textView1);

		receiver = new TestVoiceServiceBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Broadcast.IFLYTEK_INIT_FAIL);
		filter.addAction(Broadcast.IFLYTEK_RECORD_START);
		filter.addAction(Broadcast.IFLYTEK_RECORD_VOLUME_CHANGE);
		filter.addAction(Broadcast.IFLYTEK_RECORD_END);
		filter.addAction(Broadcast.IFLYTEK_VERIFY_OK);
		filter.addAction(Broadcast.IFLYTEK_VERIFY_FAIL_VOICE);
		filter.addAction(Broadcast.IFLYTEK_VERIFY_FAIL_TEXT);
		filter.addAction(Broadcast.IFLYTEK_VERIFY_FAIL_OTHER);
		LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
		conn(null);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		disconn(null);
		LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
	}

	public void conn(View v) {
		Intent service = new Intent(this, ThirdpartyService.class);
		conn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				sb.append(name.getClassName());
				sb.append("服务断开\n");
				tv.setText(sb.toString());
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				binder = (ThirdpartyServiceBinder) service;
				sb.append(name.getClassName());
				sb.append("服务连接\n");
				tv.setText(sb.toString());
			}
		};
		bindService(service, conn, BIND_AUTO_CREATE);
	}

	public void disconn(View v) {
		unbindService(conn);
	}

	public void test1(View v) throws InterruptedException {
	}

	public void test2(View v) {
	}

	public void test3(View v) {
	}

	public void test4(View v) throws InterruptedException {
	}

	class TestVoiceServiceBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			switch (intent.getAction()) {
			case Broadcast.IFLYTEK_INIT_FAIL:
				sb.append("引擎初始化失败\n");
				break;
			case Broadcast.IFLYTEK_RECORD_START:
				sb.append("开始录音\n");
				break;
			case Broadcast.IFLYTEK_RECORD_VOLUME_CHANGE:
				sb.append("音量变化");
				sb.append(intent.getIntExtra("volume", -1));
				sb.append("\n");
				break;
			case Broadcast.IFLYTEK_RECORD_END:
				sb.append("结束录音\n");
				break;
			case Broadcast.IFLYTEK_VERIFY_OK:
				sb.append("验证成功\n");
				break;
			case Broadcast.IFLYTEK_VERIFY_FAIL_VOICE:
				sb.append("验证失败声音原因\n");
				break;
			case Broadcast.IFLYTEK_VERIFY_FAIL_TEXT:
				sb.append("验证失败文本原因\n");
				break;
			case Broadcast.IFLYTEK_VERIFY_FAIL_OTHER:
				sb.append("验证失败其他原因\n");
				break;
			}
			tv.setText(sb.toString());
		}

	}
}
