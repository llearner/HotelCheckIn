package cn.edu.xmu.ultraci.hotelcheckin.client.activity;

import android.os.Bundle;
import cn.edu.xmu.ultraci.hotelcheckin.client.R;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(false, null, false, 0, R.layout.activity_main, true);
	}

}
