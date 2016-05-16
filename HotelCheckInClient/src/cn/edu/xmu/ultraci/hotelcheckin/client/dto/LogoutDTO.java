package cn.edu.xmu.ultraci.hotelcheckin.client.dto;

import java.io.Serializable;

/**
 * 对”登出“请求的响应
 * 
 * @author LuoXin
 *
 */
public class LogoutDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 4341211856729970178L;

	private Integer id;
	private String no;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
