package cn.edu.xmu.ultraci.hotelcheckin.server.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO基本操作接口
 * 
 * @author LuoXin
 * 
 */
public interface IBaseDao {
	/**
	 * 从连接池中获取一个连接
	 * 
	 * @return 数据库连接
	 * @throws SQLException 如果数据库访问出错
	 */
	public Connection getConnection() throws SQLException;

	/**
	 * 查询一行记录<br>
	 * 若结果集包含多行，只返回首行<br>
	 * 查询结果将被封装成指定类型的JavaBean
	 * 
	 * @param clazz 用于封装查询结果的JavaBean类型
	 * @param sqlStmt 要执行的查询语句
	 * @param bindArgs 绑定的参数
	 * @return 查询结果
	 * @throws SQLException 如果数据库访问出错
	 */
	public <T> T querySingleRow(Class<T> clazz, String sqlStmt, Object... bindArgs)
			throws SQLException;

	/**
	 * 查询多行记录<br>
	 * 每行查询结果先被封装成指定类型的JavaBean<br>
	 * 整个结果集再被封装成List
	 * 
	 * @param T 用于封装查询结果的JavaBean类型
	 * @param sqlStmt 要执行的查询语句
	 * @param bindArgs 绑定的参数
	 * @return 查询结果
	 * @throws SQLException 如果数据库访问出错
	 */
	public <T> List<T> queryMultiRow(Class<T> T, String sqlStmt, Object... bindArgs)
			throws SQLException;

	/**
	 * 查询一行记录中的某个字段<br>
	 * 如果结果集包含多行，只返回首行<br>
	 * 如果结果集包含多列，只返回首列
	 * 
	 * @param sqlStmt 要执行的查询语句
	 * @param bindArgs 绑定的参数
	 * @return 查询结果
	 * @throws SQLException 如果数据库访问出错
	 */
	public Object querySingleColumn(String sqlStmt, Object... bindArgs) throws SQLException;

	/**
	 * 查询多行记录中的同一个字段<br>
	 * 如果结果集包含多列，只返回首列
	 * 
	 * @param sqlStmt 要执行的查询语句
	 * @param bindArgs 绑定的参数
	 * @return 查询结果
	 * @throws SQLException 如果数据库访问出错
	 */
	public List<Object> queryMultiColumn(String sqlStmt, Object... bindArgs) throws SQLException;

	/**
	 * 插入数据
	 * 
	 * @param sqlStmt 要执行的插入语句
	 * @param bindArgs 绑定的参数
	 * @return 插入行的自增长ID
	 * @throws SQLException 如果数据库访问出错
	 */
	public BigInteger executeInsert(String sqlStmt, Object... bindArgs) throws SQLException;

	/**
	 * 更新和刪除数据
	 * 
	 * @param sqlStmt 要执行的更新语句
	 * @param bindArgs 绑定的参数
	 * @return 受影响的行数
	 * @throws SQLException 如果数据库访问出错
	 */
	public int executeUpdate(String sqlStmt, Object... bindArgs) throws SQLException;

}
