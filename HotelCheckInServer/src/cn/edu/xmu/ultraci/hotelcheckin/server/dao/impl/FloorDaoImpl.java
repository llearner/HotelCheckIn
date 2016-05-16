package cn.edu.xmu.ultraci.hotelcheckin.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IFloorDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.FloorPO;

/**
 * 楼层表DAO实现类
 * 
 * @author LuoXin
 *
 */
public class FloorDaoImpl extends BaseDaoImpl implements IFloorDao {

	private static Logger logger = LogManager.getLogger();

	@Override
	public long createFloor(FloorPO model) {
		try {
			return super.executeInsert("INSERT INTO tbl_floor(name, description) VALUES(?, ?)",
					model.getName(), model.getDescription()).longValue();
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return -1;
	}

	@Override
	public boolean updateFloor(FloorPO model) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_floor SET name = ?, description = ? WHERE id = ? AND deleted = 0",
					model.getName(), model.getDescription(), model.getId()) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public boolean deleteFloor(int id) {
		try {
			if (super.executeUpdate("UPDATE tbl_floor SET deleted = 1 WHERE id = ? AND deleted = 0",
					id) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public List<FloorPO> retrieveAllFloor() {
		try {
			return super.queryMultiRow(FloorPO.class, "SELECT * FROM tbl_floor WHERE deleted = 0",
					(Object[]) null);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public FloorPO retrieveFloorById(int id) {
		try {
			return super.querySingleRow(FloorPO.class,
					"SELECT * FROM tbl_floor WHERE id = ? AND deleted = 0", id);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

}
