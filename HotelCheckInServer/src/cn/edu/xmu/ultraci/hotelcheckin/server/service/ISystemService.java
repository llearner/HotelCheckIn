package cn.edu.xmu.ultraci.hotelcheckin.server.service;

import java.util.Map;

import cn.edu.xmu.ultraci.hotelcheckin.server.dto.BaseDTO;

/**
 * 系统类服务接口<br>
 * 
 * @author LuoXin
 *
 */
public interface ISystemService {

	/**
	 * 处理心跳请求
	 * 
	 * @param params 请求参数
	 * @return 处理结果
	 */
	public BaseDTO heartbeat(Map<String, String> params);

	/**
	 * 处理初始化请求
	 * 
	 * @param params 请求参数
	 * @return 处理结果
	 */
	public BaseDTO init(Map<String, String> params);

	/**
	 * 处理登录请求
	 * 
	 * @param params 请求参数
	 * @return 处理结果
	 */
	public BaseDTO login(Map<String, String> params);

	/**
	 * 处理登出请求
	 * 
	 * @param params 请求参数
	 * @return 处理结果
	 */
	public BaseDTO logout(Map<String, String> params);

	/**
	 * 处理声纹验证请求<br>
	 * 由客户端直接调用科大讯飞API
	 * 
	 * @param params 请求参数
	 */
	@Deprecated
	public void voiceprint(Map<String, String> params);

	/**
	 * 处理短信验证请求<br>
	 * 由客户端直接调用聚合数据API
	 * 
	 * @param params 请求参数
	 */
	@Deprecated
	public void sms(Map<String, String> params);
}
