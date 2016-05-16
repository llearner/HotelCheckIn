package cn.edu.xmu.ultraci.hotelcheckin.client.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FloorDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 3890801468222628838L;

	private List<Floor> floors;

	public FloorDTO() {
		floors = new ArrayList<Floor>();
	}

	public List<Floor> getFloors() {
		return floors;
	}

	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}

	public void addFloor(Integer id, String name) {
		Floor floor = new Floor();
		floor.setId(id);
		floor.setName(name);
		getFloors().add(floor);
	}

	public class Floor implements Serializable {
		private static final long serialVersionUID = 6754445745403901146L;

		private Integer id;
		private String name;

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
	}

}
