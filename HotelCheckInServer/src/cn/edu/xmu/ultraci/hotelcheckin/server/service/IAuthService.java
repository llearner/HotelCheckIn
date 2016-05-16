package cn.edu.xmu.ultraci.hotelcheckin.server.service;

/**
 * 鉴权服务<br>
 * 用于验证请求和合法性
 * 
 * @author LuoXin
 *
 */
public interface IAuthService {
	/**
	 * 检查鉴权参数
	 * 
	 * @param random 随机字符串
	 * @param signature 加密签名
	 * @return 鉴权结果
	 */
	public boolean doAuth(String random, String signature);
}
