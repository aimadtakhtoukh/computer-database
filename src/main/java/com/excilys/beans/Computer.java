package com.excilys.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.dao.LocalDateTimeToTimestampConverter;

/**
 * That bean is an entity representing a line of the Computer database.
 * @author excilys
 *
 */

@Entity
@Table(name = "computer")
public class Computer implements Serializable {
	private static final long serialVersionUID = -7266523565236529512L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@Convert(converter = LocalDateTimeToTimestampConverter.class)
	private LocalDateTime introduced;
	@Convert(converter = LocalDateTimeToTimestampConverter.class)
	private LocalDateTime discontinued;
	@ManyToOne
	@JoinColumn(name = "company_id", unique = false, nullable = true, insertable = true, updatable = true)
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
	
	

}
