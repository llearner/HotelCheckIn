package cn.edu.xmu.ultraci.hotelcheckin.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.LogTemplate;
import cn.edu.xmu.ultraci.hotelcheckin.server.dao.ITypeDao;
import cn.edu.xmu.ultraci.hotelcheckin.server.po.TypePO;

/**
 * 房型表DAO实现类
 * 
 * @author LuoXin
 *
 */
public class TypeDaoImpl extends BaseDaoImpl implements ITypeDao {

	private Logger logger = LogManager.getLogger();

	@Override
	public long createType(TypePO model) {
		try {
			return super.executeInsert(
					"INSERT INTO tbl_type(name, deposit, price, description) VALUES(?, ?, ?, ?)",
					model.getName(), model.getDeposit(), model.getPrice(), model.getDescription())
							.longValue();
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return -1;
	}

	@Override
	public boolean updateType(TypePO model) {
		try {
			if (super.executeUpdate(
					"UPDATE tbl_type SET name = ?, deposit = ?, price = ?, description = ? WHERE id = ? AND deleted = 0",
					model.getName(), model.getDeposit(), model.getPrice(), model.getDescription(),
					model.getId()) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public boolean deleteType(int id) {
		try {
			if (super.executeUpdate("UPDATE tbl_type SET deleted = 1 WHERE id = ? AND deleted = 0",
					id) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return false;
	}

	@Override
	public List<TypePO> retrieveAllType() {
		try {
			return super.queryMultiRow(TypePO.class, "SELECT * FROM tbl_type WHERE deleted = 0",
					(Object[]) null);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

	@Override
	public TypePO retrieveTypeById(int id) {
		try {
			return super.querySingleRow(TypePO.class,
					"SELECT * FROM tbl_type WHERE id = ? AND deleted = 0", id);
		} catch (SQLException e) {
			logger.error(LogTemplate.SQL_EXCP, e);
		}
		return null;
	}

}
