package com.excilys.services;

import java.util.List;

import com.excilys.beans.Company;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.CompanyDAOImpl;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;
	
	public static CompanyServiceImpl getInstance() {
		return INSTANCE;
	}
	
	private CompanyDAO companyDAO = CompanyDAOImpl.getInstance();
	
	public Company getCompany(long id) {
		return companyDAO.get(id);
	}
	
	public List<Company> getAllCompanies() {
		return companyDAO.getAll();
	}

}
