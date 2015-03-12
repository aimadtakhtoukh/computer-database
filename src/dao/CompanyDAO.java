package dao;

import java.util.List;

import beans.Company;

/**
 * This Database Access Object interface defines the operations
 * for Company beans.
 * 
 * @author excilys
 *
 */
public interface CompanyDAO {
	
	List<Company> getCompanies();

}
