package cn.edu.xmu.ultraci.hotelcheckin.server.dao;

import java.util.List;

import cn.edu.xmu.ultraci.hotelcheckin.server.po.GuestPO;

/**
 * 散客表DAO操作接口
 * 
 * @author LuoXin
 *
 */
public interface IGuestDao extends IBaseDao {

	/**
	 * 新增散客信息
	 * 
	 * @param model 要新增的内容
	 * @return 插入行的自增长ID
	 */
	public long createGuest(GuestPO model);

	/**
	 * 更新散客信息
	 * 
	 * @param model 要更新的内容
	 * @return 操作结果
	 */
	public boolean updateGuest(GuestPO model);

	/**
	 * 删除散客信息
	 * 
	 * @param id 要删除的ID
	 * @return 操作结果
	 */
	public boolean deleteGuest(int id);

	/**
	 * 查询所有散客信息
	 * 
	 * @return 查询结果
	 */
	public List<GuestPO> retrieveAllGuest();

	/**
	 * 根据ID查询散客信息
	 * 
	 * @param id 要查询的ID
	 * @return 查询结果
	 */
	public GuestPO retrieveGuestById(int id);
}
