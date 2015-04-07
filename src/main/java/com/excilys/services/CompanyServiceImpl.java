package com.excilys.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.beans.Company;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.CompanyDAOImpl;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDAOImpl;
import com.excilys.dao.ComputerDatabaseConnectionFactory;
import com.excilys.dao.PersistenceException;
import com.excilys.page.CompanyPage;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;
	
	final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	public static CompanyServiceImpl getInstance() {
		return INSTANCE;
	}
	
	private CompanyDAO companyDAO = CompanyDAOImpl.getInstance();	
	private ComputerDAO computerDAO = ComputerDAOImpl.getInstance();
	
	public Company getCompany(long id) {
		logger.trace("Company Service has been asked a company of id " + id);
		Company c = companyDAO.get(id);
		ComputerDatabaseConnectionFactory.getInstance().cleanConnection();
		return c;
	}
	
	public List<Company> getAllCompanies() {
		logger.trace("Company Service has been asked all companies");
		List<Company> list = companyDAO.getAll();
		ComputerDatabaseConnectionFactory.getInstance().cleanConnection();
		return list;
	}

	@Override
	public void deleteCompanyAndRelatedComputers(long id) {
		logger.trace("Company Service has been asked to delete the company with the id : " + id);
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		try {
			conn.setAutoCommit(false);
			computerDAO.deleteByCompanyId(id);
			companyDAO.delete(id);
			ComputerDatabaseConnectionFactory.getInstance().commit();
		} catch (SQLException | PersistenceException e) {
			ComputerDatabaseConnectionFactory.getInstance().rollback();
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.getInstance().cleanConnection();
		}
		
	}

	@Override
	public CompanyPage getCompanyPage() {
		return new CompanyPage(companyDAO);
	}

}
