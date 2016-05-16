package cn.edu.xmu.ultraci.hotelcheckin.server.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.edu.xmu.ultraci.hotelcheckin.server.dao.IBaseDao;

/**
 * DAO基本操作类<br>
 * 基于Apache DBUtils实现，提供CRUD方法<br>
 * 其他DAO类应当继承本类
 * 
 * @author LuoXin
 */
public class BaseDaoImpl implements IBaseDao {

	private ComboPooledDataSource ds;

	public BaseDaoImpl() {
		ds = new ComboPooledDataSource();
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	@Override
	public <T> T querySingleRow(Class<T> T, String sqlStmt, Object... bindArgs)
			throws SQLException {
		try (Connection conn = getConnection()) {
			return new QueryRunner().query(conn, sqlStmt, new BeanHandler<T>(T), bindArgs);
		}
	}

	@Override
	public <T> List<T> queryMultiRow(Class<T> T, String sqlStmt, Object... bindArgs)
			throws SQLException {
		try (Connection conn = getConnection()) {
			return new QueryRunner().query(conn, sqlStmt, new BeanListHandler<T>(T), bindArgs);
		}
	}

	@Override
	public Object querySingleColumn(String sqlStmt, Object... bindArgs) throws SQLException {
		try (Connection conn = getConnection()) {
			return new QueryRunner().query(conn, sqlStmt, new ScalarHandler<Object>(), bindArgs);
		}
	}

	@Override
	public List<Object> queryMultiColumn(String sqlStmt, Object... bindArgs) throws SQLException {
		try (Connection conn = getConnection()) {
			return new QueryRunner().query(conn, sqlStmt, new ColumnListHandler<Object>(),
					bindArgs);
		}
	}

	@Override
	public BigInteger executeInsert(String sqlStmt, Object... bindArgs) throws SQLException {
		try (Connection conn = getConnection()) {
			QueryRunner runner = new QueryRunner();
			runner.update(conn, sqlStmt, bindArgs);
			return runner.query(conn, "SELECT @@IDENTITY", new ScalarHandler<BigInteger>());
		}
	}

	@Override
	public int executeUpdate(String sqlStmt, Object... bindArgs) throws SQLException {
		try (Connection conn = getConnection()) {
			return new QueryRunner().update(conn, sqlStmt, bindArgs);
		}
	}

}
