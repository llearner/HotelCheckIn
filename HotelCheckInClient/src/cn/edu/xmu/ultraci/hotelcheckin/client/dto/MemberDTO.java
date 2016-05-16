package cn.edu.xmu.ultraci.hotelcheckin.client.dto;

import java.io.Serializable;

/**
 * 对”查询会员信息“的响应
 * 
 * @author LuoXin
 *
 */
public class MemberDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 5145329656705132222L;

	private Integer id;
	private String name;
	private String idcard;
	private String mobile;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
