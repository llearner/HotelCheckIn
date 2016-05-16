package cn.edu.xmu.ultraci.hotelcheckin.client.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import cn.edu.xmu.ultraci.hotelcheckin.client.R;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.Broadcast;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.CoreService;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.CoreService.CoreServiceBinder;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.MiscService;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.MiscService.MiscServiceBinder;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.ThirdpartyService;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.ThirdpartyService.ThirdpartyServiceBinder;
import cn.edu.xmu.ultraci.hotelcheckin.client.util.SystemUtil;

/**
 * 基础布局<br>
 * <ul>
 * 顶部：返回软键&标题&时钟&倒计时<br>
 * 中间：动态填充其他布局<br>
 * 底部：系统公告<br>
 * </ul>
 * 
 * @author LuoXin
 *
 */
public abstract class BaseActivity extends Activity {
	private static final String TAG = BaseActivity.class.getSimpleName();

	private ImageButton ibBack;
	private TextView tvTitle;
	private TextClock tcClock;
	private TextView tvCountdown;
	private LinearLayout llMain;
	private LinearLayout llBottom;
	private TextView tvNotice;

	private Handler mHandler;

	private ServiceConnection mCoreServConn;
	private ServiceConnection mMiscServConn;
	private ServiceConnection mThirdpartyServConn;

	private CoreServiceBinder mCoreServBinder;
	private MiscServiceBinder mMiscServBinder;
	private ThirdpartyServiceBinder mThirdpartyServBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService();
	}

	/**
	 * 屏蔽系统返回键
	 */
	@Override
	public void onBackPressed() {
	}

	/**
	 * 界面左上角返回软键点击事件
	 */
	public void onSoftBackPressed(View v) {
		finish();
	}

	/**
	 * 动态加载布局
	 * 
	 * @param hasBackKey
	 *            是否有返回软键
	 * @param title
	 *            界面标题
	 * @param hasCountdown
	 *            是否有倒计时
	 * @param countdown
	 *            倒计时时间
	 * @param resId
	 *            布局文件ID
	 * @param hasBottom
	 *            是否有底栏
	 */
	public void initView(boolean hasBackKey, String title, boolean hasCountdown, int countdown, int resId,
			boolean hasBottom) {
		ibBack = (ImageButton) findViewById(R.id.ib_back);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tcClock = (TextClock) findViewById(R.id.tc_clock);
		tvCountdown = (TextView) findViewById(R.id.tv_countdown);
		llMain = (LinearLayout) findViewById(R.id.ll_main);
		llBottom = (LinearLayout) findViewById(R.id.ll_buttom);
		tvNotice = (TextView) findViewById(R.id.tv_notice);

		// 隐藏返回软键
		if (!hasBackKey) {
			ibBack.setVisibility(View.GONE);
		}
		// 设置界面标题
		if (title != null) {
			tvTitle.setText(title);
		}
		// 设置显示时钟或倒计时
		if (hasCountdown) {
			tcClock.setVisibility(View.GONE);
			tvCountdown.setVisibility(View.VISIBLE);
		} else {
			tcClock.setVisibility(View.VISIBLE);
			tvCountdown.setVisibility(View.GONE);
		}
		// 配置倒计时
		if (countdown > 0) {
			mHandler = new Handler();
			mHandler.post(new Ticker(countdown));
		}
		// 设置隐藏底栏并扩大中间部分
		if (!hasBottom) {
			llBottom.setVisibility(View.GONE);
			llMain.setWeightSum((float) (8.0 / 9.0));
		} else {
			tvNotice.setText(SystemUtil.getPreferences(this, "notice"));
		}
		// 加载中间部分布局
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		llMain.addView(inflater.inflate(resId, null));
	}

	/**
	 * 绑定核心服务
	 * 
	 * @return 核心服务Binder
	 */
	public void bindCoreService() {
		mCoreServConn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.i(TAG, String.format(LogTemplate.CORE_SERIVCE_BOUND, name.getClassName()));
				mCoreServBinder = (CoreServiceBinder) service;
				SystemUtil.sendLocalBroadcast(BaseActivity.this, new Intent(Broadcast.CORE_SERIVCE_BOUND));
			}
		};
		bindService(new Intent(this, CoreService.class), mCoreServConn, BIND_AUTO_CREATE);
	}

	/**
	 * 绑定杂项服务
	 * 
	 */
	public void bindMiscService() {
		mMiscServConn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.i(TAG, String.format(LogTemplate.MISC_SERIVCE_BOUND, name.getClassName()));
				mMiscServBinder = (MiscServiceBinder) service;
				SystemUtil.sendLocalBroadcast(BaseActivity.this, new Intent(Broadcast.MISC_SERIVCE_BOUND));
			}
		};
		bindService(new Intent(this, MiscService.class), mMiscServConn, BIND_AUTO_CREATE);
	}

	/**
	 * 绑定第三方服务
	 * 
	 */
	public void bindThirdpartyService() {
		mThirdpartyServConn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.i(TAG, String.format(LogTemplate.THIRDPARTY_SERIVCE_BOUND, name.getClassName()));
				mThirdpartyServBinder = (ThirdpartyServiceBinder) service;
				SystemUtil.sendLocalBroadcast(BaseActivity.this, new Intent(Broadcast.THIRDPARTY_SERIVCE_BOUND));
			}
		};
		bindService(new Intent(this, ThirdpartyService.class), mThirdpartyServConn, BIND_AUTO_CREATE);
	}

	/**
	 * 解绑服务
	 */
	public void unbindService() {
		if (mCoreServConn != null) {
			unbindService(mCoreServConn);
			Log.i(TAG, LogTemplate.CORE_SERIVCE_UNBOUND);
		}
		if (mMiscServConn != null) {
			unbindService(mMiscServConn);
			Log.i(TAG, LogTemplate.MISC_SERIVCE_UNBOUND);
		}
		if (mThirdpartyServConn != null) {
			unbindService(mThirdpartyServConn);
			Log.i(TAG, LogTemplate.THIRDPARTY_SERIVCE_UNBOUND);
		}
	}

	/**
	 * 获取核心服务Binder<br>
	 * 确保收到核心服务绑定广播后才能调用此方法
	 * 
	 * @return 核心服务Binder
	 */
	public CoreServiceBinder getCoreServiceBinder() {
		return mCoreServBinder;
	}

	/**
	 * 获取杂项服务Binder<br>
	 * 确保收到杂项服务绑定广播后才能调用此方法
	 * 
	 * @return 核心服务Binder
	 */
	public MiscServiceBinder getMiscServiceBinder() {
		return mMiscServBinder;
	}

	/**
	 * 获取第三方服务Binder<br>
	 * 确保收到第三方服务绑定广播后才能调用此方法
	 * 
	 * @return 核心服务Binder
	 */
	public ThirdpartyServiceBinder getThirdpartyServiceBinder() {
		return mThirdpartyServBinder;
	}

	/**
	 * 倒计时任务
	 * 
	 * @author LuoXin
	 *
	 */
	class Ticker implements Runnable {
		private int countdown;

		public Ticker(int countdown) {
			this.countdown = countdown;
		}

		@Override
		public void run() {
			if ((countdown--) != 0) {
				tvCountdown.setText(String.valueOf(countdown));
			} else {
				finish();
			}
			mHandler.postDelayed(this, 1000);
		}
	}
}
