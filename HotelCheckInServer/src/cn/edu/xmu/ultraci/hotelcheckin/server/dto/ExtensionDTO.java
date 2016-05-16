package cn.edu.xmu.ultraci.hotelcheckin.server.dto;

import java.io.Serializable;

public class ExtensionDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = -8155598990645353058L;

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
