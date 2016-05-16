package cn.edu.xmu.ultraci.hotelcheckin.server.po;

import java.io.Serializable;

/**
 * 楼层表
 * 
 * @author LuoXin
 *
 */
public class FloorPO implements Serializable {
	private static final long serialVersionUID = 7015299252443583482L;

	// 序号
	private Integer id;
	// 名称
	private String name;
	// 描述
	private String description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		FloorPO other = (FloorPO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Floor [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
