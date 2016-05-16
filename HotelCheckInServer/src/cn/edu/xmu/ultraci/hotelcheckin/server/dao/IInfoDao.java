package cn.edu.xmu.ultraci.hotelcheckin.server.dao;

import java.util.List;

import cn.edu.xmu.ultraci.hotelcheckin.server.po.InfoPO;

/**
 * 基础信息表DAO操作接口
 * 
 * @author LuoXin
 *
 */
@Deprecated
public interface IInfoDao extends IBaseDao {

	/**
	 * 新增基本信息
	 * 
	 * @param model 要新增的内容
	 * @return 操作结果
	 */
	public boolean createInfo(InfoPO model);

	/**
	 * 更新基本信息
	 * 
	 * @param model 要更新的内容
	 * @return 操作结果
	 */
	public boolean updateInfo(InfoPO model);

	/**
	 * 删除基本信息
	 * 
	 * @param id 要删除的ID
	 * @return 操作结果
	 */
	public boolean deleteInfo(int id);

	/**
	 * 查询所有基本信息
	 * 
	 * @return 查询结果
	 */
	public List<InfoPO> retrieveAllInfo();

	/**
	 * 根据ID查询基本信息
	 * 
	 * @param id 要查询的ID
	 * @return 查询结果
	 */
	public InfoPO retrieveInfoById(int id);

}
