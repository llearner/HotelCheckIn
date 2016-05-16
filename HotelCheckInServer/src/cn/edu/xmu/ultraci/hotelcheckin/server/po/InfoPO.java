package cn.edu.xmu.ultraci.hotelcheckin.server.po;

import java.io.Serializable;

/**
 * 基础信息表
 * 
 * @author LuoXin
 *
 */
@Deprecated
public class InfoPO implements Serializable {
	private static final long serialVersionUID = -1299242214093013692L;

	private Integer id;
	// 酒店名称
	private String name;
	// 酒店地址
	private String address;
	// 酒店电话
	private String telephone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
		InfoPO other = (InfoPO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Info [id=" + id + ", name=" + name + ", address=" + address + ", telephone="
				+ telephone + "]";
	}

}
