package cn.edu.xmu.ultraci.hotelcheckin.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IStatusDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.StatusPO;

/**
 * 房态表DAO实现类
 * 
 * @author LuoXin
 *
 */
public class StatusDaoImpl extends BaseDaoImpl implements IStatusDao {
	private static Logger logger = LogManager.getLogger();

	@Override
	public long createStatus(StatusPO model) {
		try {
			return super.executeInsert("INSERT INTO tbl_status(name, description) VALUES(?, ?)",
					model.getName(), model.getDescription()).longValue();
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return -1;
	}

	@Override
	public boolean updateStatus(StatusPO model) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_status SET name = ?, description = ? WHERE id = ? AND deleted = 0",
					model.getName(), model.getDescription(), model.getId()) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public boolean deleteStatus(int id) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_status SET deleted = 1 WHERE id = ? AND deleted = 0", id) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public List<StatusPO> retrieveAllStatus() {
		try {
			return super.queryMultiRow(StatusPO.class, "SELECT * FROM tbl_status WHERE deleted = 0",
					(Object[]) null);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public StatusPO retrieveStatusById(int id) {
		try {
			return super.querySingleRow(StatusPO.class,
					"SELECT * FROM tbl_status WHERE id = ? AND deleted = 0", id);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

}
