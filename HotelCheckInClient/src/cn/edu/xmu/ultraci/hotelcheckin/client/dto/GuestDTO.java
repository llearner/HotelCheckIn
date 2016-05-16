package cn.edu.xmu.ultraci.hotelcheckin.client.dto;

import java.io.Serializable;

/**
 * 对”上报散客“的响应
 * 
 * @author LuoXin
 *
 */
public class GuestDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 587146328734289460L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
