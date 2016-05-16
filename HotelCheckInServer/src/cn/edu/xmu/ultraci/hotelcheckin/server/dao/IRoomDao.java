package cn.edu.xmu.ultraci.hotelcheckin.server.dao;

import java.util.List;

import cn.edu.xmu.ultraci.hotelcheckin.server.po.RoomPO;

/**
 * 房间表DAO操作接口
 * 
 * @author LuoXin
 *
 */
public interface IRoomDao extends IBaseDao {

	/**
	 * 新增房间信息
	 * 
	 * @param model 要新增的内容
	 * @return 插入行的自增长ID
	 */
	public long createRoom(RoomPO model);

	/**
	 * 更新房间信息
	 * 
	 * @param model 要更新的内容
	 * @return 操作结果
	 */
	public boolean updateRoom(RoomPO model);

	/**
	 * 删除房间信息
	 * 
	 * @param id 要删除的ID
	 * @return 操作结果
	 */
	public boolean deleteRoom(int id);

	/**
	 * 查询所有房间信息
	 * 
	 * @return 查询结果
	 */
	public List<RoomPO> retrieveAllRoom();

	/**
	 * 根据ID查询房间信息
	 * 
	 * @param id 要查询的ID
	 * @return 查询结果
	 */
	public RoomPO retrieveRoomById(int id);

	/**
	 * 根据房卡号查询房间信息
	 * 
	 * @param cardId 要查询的房卡号
	 * @return 查询结果
	 */
	public RoomPO retrieveRoomByCardId(String cardId);
}
