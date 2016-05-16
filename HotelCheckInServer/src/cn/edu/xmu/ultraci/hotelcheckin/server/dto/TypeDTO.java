package cn.edu.xmu.ultraci.hotelcheckin.server.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TypeDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = -1189213565254429353L;

	private List<Type> types;

	public TypeDTO() {
		types = new ArrayList<Type>();
	}

	public List<Type> getTypes() {
		return types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
	}

	public void addType(Integer id, String name, Double deposit, Double price) {
		Type type = new Type();
		type.setId(id);
		type.setName(name);
		type.setDeposit(deposit);
		type.setPrice(price);
		types.add(type);
	}

	public class Type implements Serializable {
		private static final long serialVersionUID = 7375496003647306319L;

		private Integer id;
		private String name;
		private Double deposit;
		private Double price;
		

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

		public Double getDeposit() {
			return deposit;
		}

		public void setDeposit(Double deposit) {
			this.deposit = deposit;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}
	}
}
