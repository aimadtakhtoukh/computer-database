package com.excilys.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.beans.Company;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.CompanyDAOImpl;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;
	
	final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	public static CompanyServiceImpl getInstance() {
		return INSTANCE;
	}
	
	private CompanyDAO companyDAO = CompanyDAOImpl.getInstance();
	
	public Company getCompany(long id) {
		logger.trace("Company Service has been asked a company of id " + id);
		return companyDAO.get(id);
	}
	
	public List<Company> getAllCompanies() {
		logger.trace("Company Service has been asked all companies");
		return companyDAO.getAll();
	}

}
