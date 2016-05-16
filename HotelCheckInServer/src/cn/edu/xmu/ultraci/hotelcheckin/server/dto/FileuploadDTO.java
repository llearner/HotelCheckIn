package cn.edu.xmu.ultraci.hotelcheckin.server.dto;

import java.io.Serializable;

public class FileuploadDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 8270432220949785570L;

	private String filename;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
