package cn.edu.xmu.ultraci.hotelcheckin.server.po;

import java.io.Serializable;

/**
 * 客户端表
 * 
 * @author LuoXin
 *
 */
public class ClientPO implements Serializable {

	private static final long serialVersionUID = -1103665762699603521L;

	// 序号
	private Integer id;
	// 客户端标识
	private String device;
	// 上次心跳
	private String heartbeat;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(String heartbeat) {
		this.heartbeat = heartbeat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientPO other = (ClientPO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", device=" + device + ", heartbeat=" + heartbeat + "]";
	}

}
