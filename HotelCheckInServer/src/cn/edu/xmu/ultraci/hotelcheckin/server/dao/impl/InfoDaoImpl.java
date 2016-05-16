package cn.edu.xmu.ultraci.hotelcheckin.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IInfoDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.InfoPO;

@Deprecated
public class InfoDaoImpl extends BaseDaoImpl implements IInfoDao {

	private Logger logger = LogManager.getLogger();

	@Override
	public boolean createInfo(InfoPO model) {
		try {
			if (super.executeUpdate(
					"INSERT INTO tbl_info(name, address, telephone) VALUES(?, ?, ?)",
					model.getName(), model.getAddress(), model.getTelephone()) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error("error while doing database operation.", e);
		}
		return false;
	}

	@Override
	public boolean updateInfo(InfoPO model) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_info SET name = ?, address = ?, telephone = ? WHERE id = ? AND deleted = 0",
					model.getName(), model.getAddress(), model.getTelephone(), model.getId()) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error("error while doing database operation.", e);
		}
		return false;
	}

	@Override
	public boolean deleteInfo(int id) {
		try {
			if (super.executeUpdate("UPDATE tbl_info SET deleted = 1 WHERE id = ? AND deleted = 0",
					id) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error("error while doing database operation.", e);
		}
		return false;
	}

	@Override
	public List<InfoPO> retrieveAllInfo() {
		try {
			return super.queryMultiRow(InfoPO.class, "SELECT * FROM tbl_info WHERE deleted = 0",
					(Object[]) null);
		} catch (SQLException e) {
			logger.error("error while doing database operation.", e);
		}
		return null;
	}

	@Override
	public InfoPO retrieveInfoById(int id) {
		try {
			return super.querySingleRow(InfoPO.class,
					"SELECT * FROM tbl_info WHERE id = ? AND deleted = 0", id);
		} catch (SQLException e) {
			logger.error("error while doing database operation.", e);
		}
		return null;
	}

}
