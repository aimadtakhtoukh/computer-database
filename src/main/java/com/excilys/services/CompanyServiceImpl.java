package com.excilys.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.beans.Company;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDatabaseConnectionFactory;
import com.excilys.dao.PersistenceException;
import com.excilys.page.CompanyPage;

@Component
public class CompanyServiceImpl implements CompanyService {
	
	final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private ComputerDatabaseConnectionFactory cdcf;
	
	public Company getCompany(long id) {
		logger.trace("Company Service has been asked a company of id " + id);
		Company c = companyDAO.get(id);
		cdcf.cleanConnection();
		return c;
	}
	
	public List<Company> getAllCompanies() {
		logger.trace("Company Service has been asked all companies");
		List<Company> list = companyDAO.getAll();
		cdcf.cleanConnection();
		return list;
	}

	@Override
	public void deleteCompanyAndRelatedComputers(long id) {
		logger.trace("Company Service has been asked to delete the company with the id : " + id);
		Connection conn = cdcf.getConnection();
		try {
			conn.setAutoCommit(false);
			computerDAO.deleteByCompanyId(id);
			companyDAO.delete(id);
			cdcf.commit();
		} catch (SQLException | PersistenceException e) {
			cdcf.rollback();
			throw new PersistenceException(e);
		} finally {
			cdcf.cleanConnection();
		}
		
	}

	@Override
	public CompanyPage getCompanyPage() {
		return new CompanyPage(companyDAO);
	}

}
