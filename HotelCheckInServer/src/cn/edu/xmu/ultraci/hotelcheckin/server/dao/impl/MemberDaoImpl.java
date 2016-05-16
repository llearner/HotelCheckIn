package cn.edu.xmu.ultraci.hotelcheckin.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IMemberDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.MemberPO;

/**
 * 会员表DAO实现类
 * 
 * @author LuoXin
 *
 */
public class MemberDaoImpl extends BaseDaoImpl implements IMemberDao {

	private static Logger logger = LogManager.getLogger();

	@Override
	public long createMember(MemberPO model) {
		try {
			return super.executeInsert(
					"INSERT INTO tbl_member(no, name, idcard, mobile, time) VALUES(?, ?, ?, ?, ?)",
					model.getNo(), model.getName(), model.getIdcard(), model.getMobile(),
					model.getTime()).longValue();
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return -1;
	}

	@Override
	public boolean updateMember(MemberPO model) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_member SET no = ?, name = ?, idcard = ?, mobile = ?, time = ? WHERE id = ? AND deleted = 0",
					model.getNo(), model.getName(), model.getIdcard(), model.getMobile(),
					model.getTime(), model.getId()) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public boolean deleteMember(int id) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_member SET deleted = 1 WHERE id = ? AND deleted = 0", id) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public List<MemberPO> retrieveAllMember() {
		try {
			return super.queryMultiRow(MemberPO.class, "SELECT * FROM tbl_member WHERE deleted = 0",
					(Object[]) null);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public MemberPO retrieveMemberById(int id) {
		try {
			return super.querySingleRow(MemberPO.class,
					"SELECT * FROM tbl_member WHERE id = ? AND deleted = 0", id);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public MemberPO retrieveMemberByCardId(String cardId) {
		try {
			return super.querySingleRow(MemberPO.class,
					"SELECT * FROM tbl_member WHERE no = ? AND deleted = 0", cardId);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

}
