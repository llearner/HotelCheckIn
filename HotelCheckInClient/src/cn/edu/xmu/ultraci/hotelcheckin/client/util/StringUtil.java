package cn.edu.xmu.ultraci.hotelcheckin.client.util;

public class StringUtil {
	/**
	 * 判断给定的字符串是否为空或空串
	 * 
	 * @param str
	 *            字符串
	 * @return 判断结果
	 */
	public static boolean isBlank(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断给定的字符串是否可以可以作为用户名<br>
	 * 用户名定义为：6-18个字母、数字或下划线的组合，并以字母开头
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isUsername(String str) {
		if (str.matches("[a-zA-Z][a-zA-Z0-9_]{5,17}")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断给定的字符串是否全为数字
	 * 
	 * @param str
	 *            字符串
	 * @return 判断结果
	 */
	public static boolean isNumeric(String str) {
		if (str.matches("\\d+")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字节数组转字符串
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 字符串
	 */
	public static String byte2HexString(byte[] bytes) {
		String ret = "";
		if (bytes != null) {
			for (Byte b : bytes) {
				ret += String.format("%02X", b.intValue() & 0xFF);
			}
		}
		return ret;
	}
}
