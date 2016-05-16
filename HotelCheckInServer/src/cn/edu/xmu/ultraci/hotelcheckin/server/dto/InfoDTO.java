package cn.edu.xmu.ultraci.hotelcheckin.server.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 对”信息查询“请求的响应
 * 
 * @author LuoXin
 *
 */
public class InfoDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = -4523224927622396186L;

	private Map<String, String> content;

	public Map<String, String> getContent() {
		return content;
	}

	public void setContent(Map<String, String> content) {
		this.content = content;
	}

}
