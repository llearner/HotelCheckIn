package cn.edu.xmu.ultraci.hotelcheckin.server.dto;

import java.io.Serializable;

public class CheckinDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = -1824469083476550994L;

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
