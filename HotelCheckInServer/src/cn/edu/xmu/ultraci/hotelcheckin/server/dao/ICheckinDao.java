package cn.edu.xmu.ultraci.hotelcheckin.server.dao;

import java.util.List;

import cn.edu.xmu.ultraci.hotelcheckin.server.po.CheckinPO;

/**
 * 住宿表DAO操作接口
 * 
 * @author LuoXin
 *
 */
public interface ICheckinDao extends IBaseDao {

	/**
	 * 新增住宿信息
	 * 
	 * @param model 要新增的内容
	 * @return 插入行的自增长ID
	 */
	public long createCheckin(CheckinPO model);

	/**
	 * 更新住宿信息
	 * 
	 * @param model 要更新的内容
	 * @return 操作结果
	 */
	public boolean updateCheckin(CheckinPO model);

	/**
	 * 删除住宿信息
	 * 
	 * @param id 要删除的ID
	 * @return 操作结果
	 */
	public boolean deleteCheckin(int id);

	/**
	 * 查询所有住宿信息
	 * 
	 * @return 查询结果
	 */
	public List<CheckinPO> retrieveAllCheckin();

	/**
	 * 根据ID查询住宿信息
	 * 
	 * @param id 要查询的ID
	 * @return 查询结果
	 */
	public CheckinPO retrieveCheckinById(int id);

	/**
	 * 根据房间ID查询住宿信息<br>
	 * 仅查询当前处于入住状态的房间
	 * 
	 * @param room 要查询的房间ID
	 * @return 查询结果
	 */
	public CheckinPO retrieveCheckinByRoom(int room);

	/**
	 * 查询当前所有处于入住状态的房间的ID
	 * 
	 * @return 查询结果
	 */
	public List<Object> retrieveAllCheckinIdWithStayFlag();
}
