package cn.edu.xmu.ultraci.hotelcheckin.client.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
	/**
	 * 获得当前系统时间
	 * 
	 * @return 系统时间
	 */
	public static long getCurrentTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 以系统默认格式格式化给定时间
	 * 
	 * @param timeMillis
	 *            要格式化的时间
	 * @return 格式化的时间
	 */
	public static String formatDateTime(long timeMillis) {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.getDefault());
		return df.format(new Date(timeMillis));
	}

	/**
	 * 以自定义格式格式化给定时间
	 * 
	 * @param timeMillis
	 *            要格式化的时间
	 * @param pattern
	 *            自定义格式
	 * @return 格式化的时间
	 */
	public static String formatDateTime(long timeMillis, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
		return sdf.format(new Date(timeMillis));
	}
}
