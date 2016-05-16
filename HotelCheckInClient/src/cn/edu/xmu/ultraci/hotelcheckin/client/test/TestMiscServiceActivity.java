package cn.edu.xmu.ultraci.hotelcheckin.client.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import cn.edu.xmu.ultraci.hotelcheckin.client.R;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.MiscService;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.MiscService.MiscServiceBinder;

public class TestMiscServiceActivity extends Activity {

	private ServiceConnection conn;
	private MiscServiceBinder binder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_misc_service);
		conn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				System.out.println(TestMiscServiceActivity.class.getSimpleName() + "服务断开");
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				System.out.println(TestMiscServiceActivity.class.getSimpleName() + "服务绑定");
				binder = (MiscServiceBinder) service;
			}
		};
		bindService(new Intent(this, MiscService.class), conn, BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);
		System.out.println(TestMiscServiceActivity.class.getSimpleName() + "服务解绑");
	}

	public void print(View v) {
		switch (v.getId()) {
		case R.id.button1:
			break;
		case R.id.button2:
			break;
		case R.id.button3:
			break;
		case R.id.button4:
			break;
		case R.id.button5:
			break;
		case R.id.button6:
			break;
		}
	}
}
