package com.excilys.services;

import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.page.Page;

public interface ComputerService {
	
	public Page<Computer> getComputerPage();
	
	public Computer getComputer(long id);
	
	public List<Computer> getAllComputers();

	public long createComputer(Computer computer);
	
	public long updateComputer(Computer computer);
	
}
