package cn.edu.xmu.ultraci.hotelcheckin.client.dto;

import java.io.Serializable;

/**
 * 对”信息查询“请求的响应
 * 
 * @author LuoXin
 *
 */
public class InfoDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = -4523224927622396186L;

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
