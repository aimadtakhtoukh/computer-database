package com.excilys.services;

import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDAOImpl;
import com.excilys.page.Page;

public enum ComputerServiceImpl implements ComputerService {
	INSTANCE;
	
	public static ComputerServiceImpl getInstance() {
		return INSTANCE;
	}
	
	private ComputerDAO computerDAO = ComputerDAOImpl.getInstance();
	
	public Page<Computer> getComputerPage() {
		return new Page<Computer>(computerDAO);
	}
	
	public List<Computer> getAllComputers() {
		return computerDAO.getAll();
	}

	public long createComputer(Computer computer) {
		return computerDAO.create(computer);
	}

}
