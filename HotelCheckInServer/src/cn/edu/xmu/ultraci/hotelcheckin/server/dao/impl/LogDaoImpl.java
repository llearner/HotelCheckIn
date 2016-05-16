package cn.edu.xmu.ultraci.hotelcheckin.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.ILogDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.LogPO;

/**
 * 日志表DAO实现类
 * 
 * @author LuoXin
 *
 */
@Deprecated
public class LogDaoImpl extends BaseDaoImpl implements ILogDao {

	private static Logger logger = LogManager.getLogger();

	@Override
	public long createLog(LogPO model) {
		try {
			return super.executeInsert("INSERT INTO tbl_log(time, device, content) VALUES(?, ?, ?)",
					model.getTime(), model.getDevice(), model.getContent()).longValue();
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return -1;
	}

	@Override
	public List<LogPO> retrieveAllLog() {
		try {
			return super.queryMultiRow(LogPO.class, "SELECT * FROM tbl_log WHERE deleted = 0",
					(Object[]) null);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public LogPO retrieveLogById(int id) {
		try {
			return super.querySingleRow(LogPO.class,
					"SELECT * FROM tbl_log WHERE id = ? AND deleted = 0", id);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

}
