package com.excilys.persistence.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	@ManyToOne
	@JoinColumn(name = "company_id", unique = false, nullable = true, insertable = true, updatable = true)
	private CompanyEntity company;
	
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
	
	public Timestamp getIntroduced() {
		return introduced;
	}
	
	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}
	
	public Timestamp getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}
	
	public CompanyEntity getCompanyEntity() {
		return company;
	}
	
	public void setCompanyEntity(CompanyEntity company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "CompanyEntity [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued + ", company="
				+ company + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ComputerEntity other = (ComputerEntity) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		
		private ComputerEntity computer;
		
		public Builder() {
			computer = new ComputerEntity();
		}
		
		public Builder id(Long id) {
			computer.id = id;
			return this;
		}
		
		public Builder name(String name) {
			computer.name = name;
			return this;
		}
		
		public Builder introduced(Timestamp introduced) {
			computer.introduced = introduced;
			return this;
		}
		
		public Builder discontinued(Timestamp discontinued) {
			computer.discontinued = discontinued;
			return this;
		}
		
		public Builder company(CompanyEntity company) {
			computer.company = company;
			return this;
		}
		
		public ComputerEntity build() {
			return computer;
		}
		
	}
	
	public static Builder builder() {
		return new ComputerEntity().new Builder();
	}
	
	
	
}
