package com.excilys.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.beans.Computer;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDatabaseConnectionFactory;
import com.excilys.page.ComputerPage;

@Service
public class ComputerServiceImpl implements ComputerService {
	
	final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
	
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private ComputerDatabaseConnectionFactory cdcf;

	@Override
	public ComputerPage getComputerPage() {
		logger.trace("Computer Service has been asked a new page.");
		return new ComputerPage(computerDAO);
	}

	@Override
	public List<Computer> getAll() {
		logger.trace("Computer Service has been asked all computers.");
		List<Computer> result = computerDAO.getAll();
		cdcf.cleanConnection();		
		return result;
	}

	@Override
	public Computer getComputer(long id) {
		logger.trace("Computer Service has been asked a computer with id : " + id);
		Computer c = computerDAO.get(id);
		cdcf.cleanConnection();		
		return c;
	}

	@Override
	public long createComputer(Computer computer) {
		logger.trace("Computer Service has been asked to create a new computer : " + computer);
		long l = computerDAO.create(computer);
		cdcf.cleanConnection();
		return l;
	}

	@Override
	public long updateComputer(Computer computer) {
		logger.trace("Computer Service has been asked to update a computer : " + computer);
		long l = computerDAO.update(computer.getId(), computer);
		cdcf.cleanConnection();
		return l;
	}

	@Override
	public void deleteComputer(long id) {
		logger.trace("Computer Service has been asked to delete the computer n°" + id);
		computerDAO.delete(id);
		cdcf.cleanConnection();
	}

}
