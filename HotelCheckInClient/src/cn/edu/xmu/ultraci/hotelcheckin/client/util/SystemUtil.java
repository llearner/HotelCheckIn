package cn.edu.xmu.ultraci.hotelcheckin.client.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiManager;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;

public class SystemUtil {
	/**
	 * 发送应用内广播
	 * 
	 * @param context
	 *            上下文
	 * @param broadcast
	 *            广播内容
	 */
	public static void sendLocalBroadcast(Context context, Intent broadcast) {
		LocalBroadcastManager.getInstance(context).sendBroadcast(broadcast);
	}

	/**
	 * 注册应用内广播接收者
	 * 
	 * @param context
	 *            上下文
	 * @param receiver
	 *            接收者
	 * @param filter
	 *            意图过滤器
	 */
	public static void registerLocalBroadcast(Context context, BroadcastReceiver receiver, IntentFilter filter) {
		LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
	}

	/**
	 * 解除注册应用内广播接收者
	 * 
	 * @param context
	 *            上下文
	 * @param receiver
	 *            接收者
	 */
	public static void unregisterLocalBroadcast(Context context, BroadcastReceiver receiver) {
		LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
	}

	/**
	 * 查询设备ID(通常为手机的IMEI或MEID)
	 * 
	 * 
	 * @param context
	 *            上下文
	 * @return 查询结果
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 查询设备无线网卡MAC地址
	 * 
	 * @param context
	 *            上下文
	 * @return 查询结果
	 */
	public static String getMacAddress(Context context) {
		WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wm.getConnectionInfo().getMacAddress();
	}

	/**
	 * 查询SharedPreferences<br>
	 * 只支持查询类型为String的值<br>
	 * 如果给定键不存在则返回null
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            键
	 * @return 值
	 */
	public static String getPreferences(Context context, String name) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getString(name, null);
	}

	/**
	 * 设置SharedPreferences
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            键
	 * @param value
	 *            值
	 */
	public static void setPreferences(Context context, String name, String value) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(name, value);
		editor.commit();
	}

}
