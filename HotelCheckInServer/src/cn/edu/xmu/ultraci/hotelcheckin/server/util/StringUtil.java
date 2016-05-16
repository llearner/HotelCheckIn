package cn.edu.xmu.ultraci.hotelcheckin.server.util;

public class StringUtil {

	/**
	 * 判断给定字符串是否为空<br>
	 * 包括空指向和空串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将给定字符串中的某一部分用*号代替<br>
	 * 用于隐藏一些敏感信息
	 * 
	 * @param str 要处理的字符串
	 * @param start 隐藏起始位置
	 * @param end 隐藏结束位置
	 * @return
	 */
	public static String shieldPartionStr(String str, int start, int end) {
		if (isBlank(str) || start < 0 || start >= str.length() || end < 0 || end >= str.length()
				|| end < start) {
			return null;
		} else {
			StringBuffer sb = new StringBuffer();
			if (start > 0) {
				sb.append(str.substring(0, start));
			}
			for (int i = 0; i < end - start + 1; i++) {
				sb.append('*');
			}
			if (str.length() - end - 1 > 0) {
				sb.append(str.substring(end + 1, str.length()));
			}
			return sb.toString();
		}
	}

}
