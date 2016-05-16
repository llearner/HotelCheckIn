package cn.edu.xmu.ultraci.hotelcheckin.server.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;

public class TimeUtil {

	private static Logger logger = LogManager.getLogger();

	/**
	 * 格式化时间<br>
	 * 在zh_CN环境下格式化为YYYY-MM-DD HH:MM:SS
	 * 
	 * @param timeMillis 要格式化的时间
	 * @return 格式化后的字符串
	 */
	public static String formatTime(long timeMillis) {
		return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
				.format(new Date(timeMillis));
	}

	/**
	 * 判断两个时间的前后关系<br>
	 * 在zh_CN环境下日期格式应为YYYY-MM-DD HH:MM:SS
	 * 
	 * @param date1 日期字符串1
	 * @param date2 日期字符串2
	 * @return 比较结果
	 */
	public static boolean isDateAfter(String date1, String date2) {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		try {
			return df.parse(date1).after(df.parse(date2));
		} catch (ParseException e) {
			logger.error(LogTemplate.PARSE_EXCP, e);
		}
		return false;
	}

	/**
	 * 判断指定时间与当前时间的前后关系<br>
	 * 在zh_CN环境下日期格式应为YYYY-MM-DD HH:MM:SS
	 * 
	 * @param date 日期字符串
	 * @return 比较结果
	 */
	public static boolean isDateAfter(String date) {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		try {
			return df.parse(date).after(new Date());
		} catch (ParseException e) {
			logger.error(LogTemplate.PARSE_EXCP, e);
		}
		return false;
	}
}
