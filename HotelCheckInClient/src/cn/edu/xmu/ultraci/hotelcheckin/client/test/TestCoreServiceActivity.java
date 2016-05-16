package cn.edu.xmu.ultraci.hotelcheckin.client.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import cn.edu.xmu.ultraci.hotelcheckin.client.R;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.CoreService;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.CoreService.CoreServiceBinder;

public class TestCoreServiceActivity extends Activity {

	private ServiceConnection conn;
	private CoreServiceBinder binder;

	private EditText etParam1;
	private EditText etParam2;
	private EditText etParam3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_core_service);

		etParam1 = (EditText) findViewById(R.id.editText1);
		etParam2 = (EditText) findViewById(R.id.editText2);
		etParam3 = (EditText) findViewById(R.id.editText3);

		conn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				System.out.println("服务异常");
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				binder = (CoreServiceBinder) service;
				System.out.println("服务绑定");
			}
		};
		bindService(new Intent(this, CoreService.class), conn, BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		unbindService(conn);
		System.out.println("服务解绑");
	}

	public void onClick(View v) {
		String param1 = etParam1.getText().toString().trim();
		String param2 = etParam2.getText().toString().trim();
		String param3 = etParam3.getText().toString().trim();

		switch (v.getId()) {
		case R.id.button1:
			binder.heartbeat();
			break;
		case R.id.button2:
			binder.init();
			break;
		case R.id.button3:
			binder.login(param1);
			break;
		case R.id.button4:
			binder.logout(param1);
			break;
		case R.id.button5:
			binder.member(param1);
			break;
		case R.id.button6:
			binder.type();
			break;
		case R.id.button7:
			binder.floor();
			break;
		case R.id.button8:
			binder.status(param1, param2);
			break;
		case R.id.button9:
			binder.room(param1);
			break;
		case R.id.button10:
			binder.guest(param1, param2);
			break;
		case R.id.button11:
			binder.checkin(param1, param2, param3);
			break;
		case R.id.button12:
			binder.checkout(param1);
			break;
		case R.id.button13:
			binder.upload(param1, param2);
			break;
		case R.id.button14:
			binder.download(param1, param2);
			break;
		}
	}
}
