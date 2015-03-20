package servlet.dto;

import beans.Computer;


public class ComputerDTO {
	
	private String name;
	private String introduced;
	private String discontinued;
	private String company;
	
	public ComputerDTO() {
		
	}
	
	public ComputerDTO(Computer computer) {
		name = computer.getName();
		if (computer.getIntroduced() != null) {
			introduced = computer.getIntroduced().toLocalDate().toString();
		} else {
			introduced = null;
		}
		if (computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued().toLocalDate().toString();
		} else {
			discontinued = null;
		}
		if (computer.getCompany() != null) {
			company = computer.getCompany().getName();
		} else {
			company = null;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIntroduced() {
		return introduced;
	}
	
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}

}
