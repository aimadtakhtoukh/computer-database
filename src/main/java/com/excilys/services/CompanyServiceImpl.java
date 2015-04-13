package com.excilys.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.beans.Company;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.PersistenceException;
import com.excilys.page.CompanyPage;

@Component
public class CompanyServiceImpl implements CompanyService {
	
	final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private ComputerDAO computerDAO;
	
	public Company getCompany(long id) {
		logger.trace("Company Service has been asked a company of id " + id);
		return companyDAO.get(id);
	}
	
	public List<Company> getAllCompanies() {
		logger.trace("Company Service has been asked all companies");
		return companyDAO.getAll();
	}

	@Override
	@Transactional(rollbackFor={SQLException.class, PersistenceException.class})
	public void deleteCompanyAndRelatedComputers(long id) {
		logger.trace("Company Service has been asked to delete the company with the id : " + id);
		computerDAO.deleteByCompanyId(id);
		companyDAO.delete(id);
	}

	@Override
	public CompanyPage getCompanyPage() {
		return new CompanyPage(companyDAO);
	}

}
