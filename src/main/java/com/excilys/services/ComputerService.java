package com.excilys.services;

import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.page.ComputerPage;

public interface ComputerService {
	
	public ComputerPage getComputerPage();
	
	public Computer getComputer(long id);
	
	public List<Computer> getAll();

	public long createComputer(Computer computer);
	
	public long updateComputer(Computer computer);
	
	public void deleteComputer(long id);
	
}
