package cn.edu.xmu.ultraci.hotelcheckin.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IClientDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.ClientPO;

/**
 * 客户端表DAO实现类
 * 
 * @author LuoXin
 *
 */
public class ClientDaoImpl extends BaseDaoImpl implements IClientDao {

	private static Logger logger = LogManager.getLogger();

	@Override
	public long createClient(ClientPO model) {
		try {
			return super.executeInsert("INSERT INTO tbl_client(device, heartbeat) VALUES(?, ?)",
					model.getDevice(), model.getHeartbeat()).longValue();
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return -1;
	}

	@Override
	public boolean updateClient(ClientPO model) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_client SET device = ?, heartbeat = ? WHERE id = ? AND deleted = 0",
					model.getDevice(), model.getHeartbeat(), model.getId()) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public boolean deleteClient(int id) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_client SET deleted = 1 WHERE id = ? AND deleted = 0", id) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public List<ClientPO> retrieveAllClient() {
		try {
			return super.queryMultiRow(ClientPO.class, "SELECT * FROM tbl_client WHERE deleted = 0",
					(Object[]) null);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public ClientPO retrieveClientById(int id) {
		try {
			return super.querySingleRow(ClientPO.class,
					"SELECT * FROM tbl_client WHERE id = ? AND deleted = 0", id);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public ClientPO retrieveClientByDevice(String device) {
		try {
			return super.querySingleRow(ClientPO.class,
					"SELECT * FROM tbl_client WHERE device = ? AND deleted = 0", device);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

}
