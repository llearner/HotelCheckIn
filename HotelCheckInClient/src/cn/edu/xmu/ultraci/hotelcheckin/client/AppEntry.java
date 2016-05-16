package cn.edu.xmu.ultraci.hotelcheckin.client;

import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechUtility;

import android.app.Application;
import android.content.Intent;
import cn.edu.xmu.ultraci.hotelcheckin.client.constant.Config;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.CoreService;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.MiscService;
import cn.edu.xmu.ultraci.hotelcheckin.client.service.ThirdpartyService;
import cn.smssdk.SMSSDK;

/**
 * 应用程序入口
 * 
 * @author LuoXin
 *
 */
public class AppEntry extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化科大讯飞语音云
		SpeechUtility.createUtility(this, "appid=" + Config.IFLYTEK_APPID);
		Setting.setShowLog(false);
		// 初始化掌淘科技短信验证码
		SMSSDK.initSDK(this, Config.MOB_APP_KEY, Config.MOB_APP_SECRET);
		// 启动服务
		startService(new Intent(this, CoreService.class));
		startService(new Intent(this, MiscService.class));
		startService(new Intent(this, ThirdpartyService.class));
	}
}
