package cn.edu.xmu.ultraci.hotelcheckin.server.service;

/**
 * 系统配置服务接口
 * 
 * @author LuoXin
 *
 */
public interface IConfService {

	/**
	 * 读系统配置项
	 * 
	 * @param key 配置项名
	 * @return 配置项值
	 */
	public String getConf(String key);

	/**
	 * 写系统配置项
	 * 
	 * @param key 配置项名
	 * @param value 配置项值
	 */
	public void setConf(String key, String value);
}
