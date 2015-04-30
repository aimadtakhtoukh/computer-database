package com.excilys.service.services;

import java.util.List;

import com.excilys.core.beans.Company;
import com.excilys.persistence.page.CompanyPage;
/**
 * Service interface for Companies.
 * @author excilys
 *
 */
public interface CompanyService {
	
	/**
	 * Returns a page with companies.
	 * @return
	 */
	public CompanyPage getCompanyPage();
	
	/**
	 * Returns a company bean with the same id than the parameter.
	 * @param id
	 * @return
	 */	
	public Company getCompany(long id);
	
	/**
	 * Returns a list of all Companies.
	 * @return
	 */
	public List<Company> getAllCompanies();

	/**
	 * Deletes a company in the database, and all computers
	 * with the same company id.
	 * @param id
	 */
	public void deleteCompanyAndRelatedComputers(long id);

}
