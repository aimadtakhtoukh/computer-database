package dao;

import java.sql.SQLException;
import java.util.List;

import beans.Company;

/**
 * This Database Access Object interface defines the CRUD operations
 * for Company beans.
 * 
 * @author excilys
 *
 */
public interface CompanyDAO {
	
	/**
	 * 
	 * @param computer
	 * @return 
	 */
	long createCompany(Company company);
	
	/**
	 * 
	 * @param id
	 * @param company
	 * @return 
	 */
	long updateCompany(long id, Company company);
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	Company getCompany(long id) throws SQLException;
	
	/**
	 * 
	 * @param id
	 * @return 
	 */
	long deleteCompany(long id);
	
	/**
	 * 
	 * @return
	 * @throws SQLException 
	 */
	List<Company> getAllCompanies() throws SQLException;

}
