package dao;

import java.util.List;

import beans.Computer;

/**
 * This Database Access Object interface defines the operations
 * for Computer beans.
 * @author excilys
 *
 */

public interface ComputerDAO {
	
	void createComputer(Computer c);
	
	void updateComputer(long id, Computer c);
	
	Computer getComputer(long id);
	
	void deleteComputer(long id);
	
	List<Computer> getAllComputers();
}
