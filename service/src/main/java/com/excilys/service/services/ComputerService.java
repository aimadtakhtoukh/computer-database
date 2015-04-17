package com.excilys.service.services;

import java.util.List;

import com.excilys.core.beans.Computer;
import com.excilys.persistence.page.ComputerPage;

public interface ComputerService {
	
	public ComputerPage getComputerPage();
	
	public Computer getComputer(long id);
	
	public List<Computer> getAll();

	public long createComputer(Computer computer);
	
	public long updateComputer(Computer computer);
	
	public void deleteComputer(long id);
	
}
