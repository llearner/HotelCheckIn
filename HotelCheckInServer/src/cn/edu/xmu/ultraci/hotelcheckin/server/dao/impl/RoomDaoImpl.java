package cn.edu.xmu.ultraci.hotelcheckin.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IRoomDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.RoomPO;

/**
 * 房间表DAO操作类
 * 
 * @author LuoXin
 *
 */
public class RoomDaoImpl extends BaseDaoImpl implements IRoomDao {

	private static Logger logger = LogManager.getLogger();

	@Override
	public long createRoom(RoomPO model) {
		try {
			return super.executeInsert(
					"INSERT INTO tbl_room(no, name, floor, type, status, description) VALUES(?, ?, ?, ?, ?, ?)",
					model.getNo(), model.getName(), model.getFloor(), model.getType(),
					model.getStatus(), model.getDescription()).longValue();
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return -1;
	}

	@Override
	public boolean updateRoom(RoomPO model) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_room SET no = ?, name=?, floor = ?, type = ?, status = ?, description = ? WHERE id = ? AND deleted = 0",
					model.getNo(), model.getName(), model.getFloor(), model.getType(),
					model.getStatus(), model.getDescription(), model.getId()) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public boolean deleteRoom(int id) {
		try {
			if (super.executeUpdate("UPDATE tbl_room SET deleted = 1 WHERE id = ? AND deleted = 0",
					id) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public List<RoomPO> retrieveAllRoom() {
		try {
			return super.queryMultiRow(RoomPO.class, "SELECT * FROM tbl_room WHERE deleted = 0",
					(Object[]) null);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public RoomPO retrieveRoomById(int id) {
		try {
			return super.querySingleRow(RoomPO.class,
					"SELECT * FROM tbl_room WHERE id = ? AND deleted = 0", id);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public RoomPO retrieveRoomByCardId(String cardId) {
		try {
			return super.querySingleRow(RoomPO.class,
					"SELECT * FROM tbl_room WHERE no = ? AND deleted = 0", cardId);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

}
