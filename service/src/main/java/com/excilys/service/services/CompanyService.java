package com.excilys.service.services;

import java.util.List;

import com.excilys.core.beans.Company;
import com.excilys.persistence.page.CompanyPage;

public interface CompanyService {
	
	public CompanyPage getCompanyPage();
	
	public Company getCompany(long id);
	
	public List<Company> getAllCompanies();

	public void deleteCompanyAndRelatedComputers(long id);

}
