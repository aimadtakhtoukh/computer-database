package com.excilys.services;

import java.util.List;

import com.excilys.beans.Company;

public interface CompanyService {
	
	public Company getCompany(long id);
	
	public List<Company> getAllCompanies();


}
