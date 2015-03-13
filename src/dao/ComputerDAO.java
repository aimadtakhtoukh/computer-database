package dao;

import java.util.List;

import beans.Computer;

/**
 * This Database Access Object interface defines the CRUD operations
 * for Computer beans.
 * @author excilys
 *
 */

public interface ComputerDAO {
	
	/**
	 * 
	 * @param computer
	 */
	void createComputer(Computer computer);
	
	/**
	 * 
	 * @param id
	 * @param computer
	 */
	void updateComputer(long id, Computer computer);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Computer getComputer(long id);
	
	/**
	 * 
	 * @param id
	 */
	void deleteComputer(long id);
	
	/**
	 * 
	 * @return
	 */
	List<Computer> getAllComputers();
}
