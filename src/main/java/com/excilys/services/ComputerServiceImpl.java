package com.excilys.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.beans.Computer;
import com.excilys.dao.ComputerDAO;
import com.excilys.page.ComputerPage;

@Service
public class ComputerServiceImpl implements ComputerService {
	
	final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
	
	@Autowired
	private ComputerDAO computerDAO;

	@Override
	public ComputerPage getComputerPage() {
		logger.trace("Computer Service has been asked a new page.");
		return new ComputerPage(computerDAO);
	}

	@Override
	public List<Computer> getAll() {
		logger.trace("Computer Service has been asked all computers.");
		return computerDAO.getAll();
	}

	@Override
	public Computer getComputer(long id) {
		logger.trace("Computer Service has been asked a computer with id : " + id);
		return computerDAO.get(id);
	}

	@Override
	public long createComputer(Computer computer) {
		logger.trace("Computer Service has been asked to create a new computer : " + computer);
		return computerDAO.create(computer);
	}

	@Override
	public long updateComputer(Computer computer) {
		logger.trace("Computer Service has been asked to update a computer : " + computer);
		return computerDAO.update(computer.getId(), computer);
	}

	@Override
	public void deleteComputer(long id) {
		logger.trace("Computer Service has been asked to delete the computer n°" + id);
		computerDAO.delete(id);
	}

}
