package cn.edu.xmu.ultraci.hotelcheckin.server.po;

import java.io.Serializable;

/**
 * 住宿表
 * 
 * @author LuoXin
 *
 */
public class CheckinPO implements Serializable {
	private static final long serialVersionUID = -4712562884864553515L;

	// 序号
	private Integer id;
	// 房号
	private Integer room;
	// 会员序号
	private Integer member;
	// 散客序号
	private Integer guest;
	// 在住标志
	private Integer stay;
	// 入住时间
	private String checkin;
	// (拟)退房时间
	private String checkout;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	public Integer getMember() {
		return member;
	}

	public void setMember(Integer member) {
		this.member = member;
	}

	public Integer getGuest() {
		return guest;
	}

	public void setGuest(Integer guest) {
		this.guest = guest;
	}

	public Integer getStay() {
		return stay;
	}

	public void setStay(Integer stay) {
		this.stay = stay;
	}

	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getCheckout() {
		return checkout;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
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
		CheckinPO other = (CheckinPO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Checkin [id=" + id + ", room=" + room + ", member=" + member + ", guest=" + guest
				+ ", stay=" + stay + ", checkin=" + checkin + ", checkout=" + checkout + "]";
	}

}
