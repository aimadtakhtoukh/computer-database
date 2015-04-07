package com.excilys.services;

import java.util.List;

import com.excilys.beans.Company;
import com.excilys.page.CompanyPage;

public interface CompanyService {
	
	public CompanyPage getCompanyPage();
	
	public Company getCompany(long id);
	
	public List<Company> getAllCompanies();

	public void deleteCompanyAndRelatedComputers(long id);

}
