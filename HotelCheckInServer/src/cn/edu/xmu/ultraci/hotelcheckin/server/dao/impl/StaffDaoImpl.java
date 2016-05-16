package cn.edu.xmu.ultraci.hotelcheckin.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IStaffDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.StaffPO;

/**
 * 员工表DAO实现类
 * 
 * @author LuoXin
 *
 */
public class StaffDaoImpl extends BaseDaoImpl implements IStaffDao {

	private static Logger logger = LogManager.getLogger();

	@Override
	public long createStaff(StaffPO model) {
		try {
			return super.executeInsert(
					"INSERT INTO tbl_staff(no, name, voiceprint, privilege, time) VALUES(?, ?, ?, ?, ?)",
					model.getNo(), model.getName(), model.getVoiceprint(), model.getPrivilege(),
					model.getTime()).longValue();
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return -1;
	}

	@Override
	public boolean updateStaff(StaffPO model) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_staff SET no = ?, name = ?, voiceprint = ?, privilege = ?, time = ? WHERE id = ? AND deleted = 0",
					model.getNo(), model.getName(), model.getVoiceprint(), model.getPrivilege(),
					model.getTime(), model.getId()) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public boolean deleteStaff(int id) {
		try {
			if (super.executeUpdate("UPDATE tbl_staff SET deleted = 1 WHERE id = ? AND deleted = 0",
					id) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public List<StaffPO> retrieveAllStaff() {
		try {
			return super.queryMultiRow(StaffPO.class, "SELECT * FROM tbl_staff WHERE deleted = 0",
					(Object[]) null);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public StaffPO retrieveStaffById(int id) {
		try {
			return super.querySingleRow(StaffPO.class,
					"SELECT * FROM tbl_staff WHERE id = ? AND deleted = 0", id);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public StaffPO retrieveStaffByCardId(String cardId) {
		try {
			return super.querySingleRow(StaffPO.class,
					"SELECT * FROM tbl_staff WHERE no = ? AND deleted = 0", cardId);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

}
