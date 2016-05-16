package cn.edu.xmu.ultraci.hotelcheckin.server.dto;

import java.io.Serializable;

import cn.edu.xmu.ultraci.hotelcheckin.server.constant.ErrorCode;

/**
 * 基本响应结构
 * 
 * @author LuoXin
 *
 */
public class BaseDTO implements Serializable {
	private static final long serialVersionUID = -8112172034792307819L;

	private Integer result;
	private Long timestamp;

	public BaseDTO() {
		this.setResult(ErrorCode.OK);
		this.setTimestamp(System.currentTimeMillis());
	}

	public BaseDTO(Integer result) {
		this.setResult(result);
		this.setTimestamp(System.currentTimeMillis());
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
