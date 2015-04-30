package com.excilys.core.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.excilys.core.serialization.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * That bean is used to represent a Computer through the whole platform.
 * @author excilys
 *
 */

public class Computer implements Serializable {
	private static final long serialVersionUID = -7266523565236529512L;
	
	private Long id;
	private String name;
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime introduced;
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime discontinued;
	private Company company;
	
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
	
	public LocalDateTime getIntroduced() {
		return introduced;
	}
	
	public void setIntroduced(LocalDateTime introduced) {
		this.introduced = introduced;
	}
	
	public LocalDateTime getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(LocalDateTime discontinued) {
		this.discontinued = discontinued;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return new StringBuffer()
				.append("Computer [id=")
				.append(id)
				.append(", name=")
				.append(name)
				.append(", introduced=")
				.append(introduced)
				.append(", discontinued=")
				.append(discontinued)
				.append(", company=")
				.append(company)
				.append("]")
				.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((introduced == null) ? 0 : introduced.hashCode());
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
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	public class Builder {
		
		private Computer computer;
		
		public Builder() {
			computer = new Computer();
		}
		
		public Builder id(Long id) {
			computer.id = id;
			return this;
		}
		
		public Builder name(String name) {
			computer.name = name;
			return this;
		}
		
		public Builder introduced(LocalDateTime introduced) {
			computer.introduced = introduced;
			return this;
		}
		
		public Builder discontinued(LocalDateTime discontinued) {
			computer.discontinued = discontinued;
			return this;
		}
		
		public Builder company(Company company) {
			computer.company = company;
			return this;
		}
		
		public Computer build() {
			return computer;
		}
		
	}
	
	public static Builder builder() {
		return new Computer().new Builder();
	}

}
