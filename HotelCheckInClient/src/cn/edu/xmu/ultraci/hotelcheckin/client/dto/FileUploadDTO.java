package cn.edu.xmu.ultraci.hotelcheckin.client.dto;

import java.io.Serializable;

public class FileUploadDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 8270432220949785570L;

	private String filename;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
