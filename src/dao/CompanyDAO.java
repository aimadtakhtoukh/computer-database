package dao;

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
	 */
	void createCompany(Company company);
	
	/**
	 * 
	 * @param id
	 * @param company
	 */
	void updateCompany(long id, Company company);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Company getCompany(long id);
	
	/**
	 * 
	 * @param id
	 */
	void deleteCompany(long id);
	
	/**
	 * 
	 * @return
	 */
	List<Company> getAllCompanies();

}
