package dao;

import java.sql.SQLException;
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
	 * @return 
	 * @throws SQLException 
	 */
	long createComputer(Computer computer) throws SQLException;
	
	/**
	 * 
	 * @param id
	 * @param computer
	 * @return 
	 * @throws SQLException 
	 */
	long updateComputer(long id, Computer computer) throws SQLException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	Computer getComputer(long id) throws SQLException;
	
	/**
	 * 
	 * @param id
	 * @return 
	 * @throws SQLException 
	 */
	long deleteComputer(long id) throws SQLException;
	
	/**
	 * 
	 * @return
	 * @throws SQLException 
	 */
	List<Computer> getAllComputers() throws SQLException;
}
