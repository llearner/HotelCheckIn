package cn.edu.xmu.ultraci.hotelcheckin.server.po;

import java.io.Serializable;

/**
 * 员工表
 * 
 * @author LuoXin
 *
 */
public class StaffPO implements Serializable {
	private static final long serialVersionUID = -8322019473302819789L;

	// 序号
	private Integer id;
	// 卡号
	private String no;
	// 姓名
	private String name;
	// 声纹
	private String voiceprint;
	// 特权
	private Integer privilege;
	// 入职时间
	private String time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVoiceprint() {
		return voiceprint;
	}

	public void setVoiceprint(String voiceprint) {
		this.voiceprint = voiceprint;
	}

	public Integer getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Integer privilege) {
		this.privilege = privilege;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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
		StaffPO other = (StaffPO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stuff [id=" + id + ", no=" + no + ", name=" + name + ", voiceprint=" + voiceprint
				+ ", privilege=" + privilege + ", time=" + time + "]";
	}

}
