package com.excilys.service.services;

import java.util.List;

import com.excilys.core.beans.Computer;
import com.excilys.persistence.page.ComputerPage;
/**
 * Service interface for Computers.
 * @author excilys
 *
 */
public interface ComputerService {
	
	/**
	 * Returns a page with computers.
	 * @return
	 */
	public ComputerPage getComputerPage();
	
	/**
	 * Returns a computer bean with the same id than the parameter.
	 * @param id
	 * @return
	 */
	public Computer getComputer(long id);
	
	/**
	 * Returns a list of all Computers.
	 * @return
	 */
	public List<Computer> getAll();

	/**
	 * Adds a computer to the database.
	 * @param computer
	 * @return
	 */
	public long createComputer(Computer computer);
	
	/**
	 * Edits a computer in the database, with
	 * the same id than the computer's id.
	 * @param computer
	 * @return
	 */
	public long updateComputer(Computer computer);
	
	/**
	 * Deletes a computer in the database, with 
	 * the same id than the parameter.
	 * @param id
	 */
	public void deleteComputer(long id);
	
}
