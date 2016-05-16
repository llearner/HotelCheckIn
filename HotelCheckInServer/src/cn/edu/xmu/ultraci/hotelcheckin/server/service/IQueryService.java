package cn.edu.xmu.ultraci.hotelcheckin.server.service;

import java.util.Map;

import cn.edu.xmu.ultraci.hotelcheckin.server.dto.BaseDTO;

/**
 * 查询类服务接口<br>
 * 
 * @author LuoXin
 *
 */
public interface IQueryService {

	/**
	 * 查询会员信息
	 * 
	 * @param params 参数集
	 * @return 查询结果
	 */
	public BaseDTO queryMember(Map<String, String> params);

	/**
	 * 查询房型信息
	 * 
	 * @param params 参数集
	 * @return 查询结果
	 */
	public BaseDTO queryType(Map<String, String> params);

	/**
	 * 查询楼层信息
	 * 
	 * @param params 参数集
	 * @return 查询结果
	 */
	public BaseDTO queryFloor(Map<String, String> params);

	/**
	 * 查询房态信息
	 * 
	 * @param params 参数集
	 * @return 查询结果
	 */
	public BaseDTO queryStatus(Map<String, String> params);

	/**
	 * 查询房间信息
	 * 
	 * @param params 参数集
	 * @return 查询结果
	 */
	public BaseDTO queryRoom(Map<String, String> params);

	/**
	 * 查询其他信息
	 * 
	 * @param params 参数集
	 * @return 查询结果
	 */
	public BaseDTO queryInfo(Map<String, String> params);
}
