package cn.edu.xmu.ultraci.hotelcheckin.server.dao;

import java.util.List;

import cn.edu.xmu.ultraci.hotelcheckin.server.po.MemberPO;

/**
 * 会员表DAO操作接口
 * 
 * @author LuoXin
 *
 */
public interface IMemberDao extends IBaseDao {

	/**
	 * 新增会员信息
	 * 
	 * @param model 要新增的内容
	 * @return 插入行的自增长ID
	 */
	public long createMember(MemberPO model);

	/**
	 * 更新会员信息
	 * 
	 * @param model 要更新的内容
	 * @return 操作结果
	 */
	public boolean updateMember(MemberPO model);

	/**
	 * 删除会员信息
	 * 
	 * @param id 要删除的ID
	 * @return 操作结果
	 */
	public boolean deleteMember(int id);

	/**
	 * 查询所有会员信息
	 * 
	 * @return 查询结果
	 */
	public List<MemberPO> retrieveAllMember();

	/**
	 * 根据ID查询会员信息
	 * 
	 * @param id 要查询的ID
	 * @return 查询结果
	 */
	public MemberPO retrieveMemberById(int id);

	/**
	 * 根据会员卡号查询会员信息
	 * 
	 * @param cardid 要查询的会员卡号
	 * @return 查询结果
	 */
	public MemberPO retrieveMemberByCardId(String cardId);
}
