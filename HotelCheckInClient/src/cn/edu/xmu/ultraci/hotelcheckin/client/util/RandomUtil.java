package cn.edu.xmu.ultraci.hotelcheckin.client.util;

import java.util.Random;

public class RandomUtil {
	private static final String BASE_NUMBER = "0123456789";
	private static final String BASE_CHARACTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/**
	 * 生成随机数字串
	 * 
	 * @param length
	 *            串长
	 * @return 随机串
	 */
	public static String generateRandomNum(int length) {
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(BASE_CHARACTER.charAt(rand.nextInt(BASE_NUMBER.length())));
		}
		return sb.toString();
	}

	/**
	 * 生成随机字符串
	 * 
	 * @param length
	 *            串长
	 * @return 随机串
	 */
	public static String generateRandomStr(int length) {
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(BASE_CHARACTER.charAt(rand.nextInt(BASE_CHARACTER.length())));
		}
		return sb.toString();
	}
}
