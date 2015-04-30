package com.excilys.core.beans;

import java.io.Serializable;

/**
 * That bean is used to represent a Company through the whole platform.
 * @author excilys
 *
 */

public class Company implements Serializable {
	private static final long serialVersionUID = -1302047317794536556L;
	
	private Long id;
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return new StringBuffer()
				.append("Company [id=")
				.append(id)
				.append(", name=")
				.append(name)
				.append("]")
				.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public class Builder {
		
		private Company company;
		
		public Builder() {
			company = new Company();
		}
		
		public Builder id(Long id) {
			company.id = id;
			return this;
		}
		
		public Builder name(String name) {
			company.name = name;
			return this;
		}
		
		public Company build() {
			return company;
		}
		
	}
	
	public static Builder builder() {
		return new Company().new Builder();
	}

}
