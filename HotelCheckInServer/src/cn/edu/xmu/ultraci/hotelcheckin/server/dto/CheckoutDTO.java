package cn.edu.xmu.ultraci.hotelcheckin.server.dto;

import java.io.Serializable;

public class CheckoutDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = -3078600975988585353L;

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
