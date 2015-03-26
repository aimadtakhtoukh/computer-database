package com.excilys.services;

import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.page.PageImpl;

public interface ComputerService {
	
	public PageImpl<Computer> getComputerPage();
	
	public Computer getComputer(long id);
	
	public List<Computer> getAllComputers();

	public long createComputer(Computer computer);
	
	public long updateComputer(Computer computer);
	
	public void deleteComputer(long id);
	
}
