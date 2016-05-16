package cn.edu.xmu.ultraci.hotelcheckin.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IGuestDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.GuestPO;

/**
 * 散客表DAO实现类
 * 
 * @author LuoXin
 *
 */
public class GuestDaoImpl extends BaseDaoImpl implements IGuestDao {

	private static Logger logger = LogManager.getLogger();

	@Override
	public long createGuest(GuestPO model) {
		try {
			return super.executeInsert(
					"INSERT INTO tbl_guest(mobile, idcard, time) VALUES(?, ?, ?)",
					model.getMobile(), model.getIdcard(), model.getTime()).longValue();
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return -1;
	}

	@Override
	public boolean updateGuest(GuestPO model) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_guest SET mobile = ?, idcard = ?, time = ? WHERE id = ? AND deleted = 0",
					model.getMobile(), model.getIdcard(), model.getTime(), model.getId()) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public boolean deleteGuest(int id) {
		try {
			if (super.executeUpdate("UPDATE tbl_guest SET deleted = 1 WHERE id = ? AND deleted = 0",
					id) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public List<GuestPO> retrieveAllGuest() {
		try {
			return super.queryMultiRow(GuestPO.class, "SELECT * FROM tbl_guest WHERE deleted = 0",
					(Object[]) null);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public GuestPO retrieveGuestById(int id) {
		try {
			return super.querySingleRow(GuestPO.class,
					"SELECT * FROM tbl_guest WHERE id = ? AND deleted = 0", id);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

}
