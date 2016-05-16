package cn.edu.xmu.ultraci.hotelcheckin.server.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StatusDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = -8631939639799581762L;

	private List<Status> statuses;

	public StatusDTO() {
		statuses = new ArrayList<Status>();
	}

	public List<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

	public void addStatus(Integer id, String name, Integer floor, Integer type, Integer available) {
		Status status = new Status();
		status.setId(id);
		status.setName(name);
		status.setFloor(floor);
		status.setType(type);
		status.setAvailable(available);
		getStatuses().add(status);
	}

	public class Status implements Serializable {
		private static final long serialVersionUID = 6517552528397367486L;

		private Integer id;
		private String name;
		private Integer floor;
		private Integer type;
		private Integer available;

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

		public Integer getFloor() {
			return floor;
		}

		public void setFloor(Integer floor) {
			this.floor = floor;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Integer getAvailable() {
			return available;
		}

		public void setAvailable(Integer available) {
			this.available = available;
		}
	}

}
