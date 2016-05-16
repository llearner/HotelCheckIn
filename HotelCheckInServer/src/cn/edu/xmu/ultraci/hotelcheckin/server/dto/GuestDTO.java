package cn.edu.xmu.ultraci.hotelcheckin.server.dto;

import java.io.Serializable;

/**
 * 对”上报散客“的响应
 * 
 * @author LuoXin
 *
 */
public class GuestDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 587146328734289460L;

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
