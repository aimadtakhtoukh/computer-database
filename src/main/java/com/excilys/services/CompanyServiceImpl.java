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
		return companyDAO.get(id);
	}
	
	public List<Company> getAllCompanies() {
		logger.trace("Company Service has been asked all companies");
		return companyDAO.getAll();
	}

	@Override
	public void deleteCompanyAndRelatedComputers(long id) {
		logger.trace("Company Service has been asked to delete the company with the id : " + id);
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		try {
			conn.setAutoCommit(false);
			computerDAO.getAll()
			.stream()
			.filter(computer -> computer.getCompany() != null)
			.filter(computer -> (computer.getCompany().getId() == id))
			.forEach(computer -> computerDAO.delete(computer.getId(), conn));
			companyDAO.delete(id, conn);
			conn.commit();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} catch (PersistenceException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw e;
			}
			throw e;
		} finally {
			ComputerDatabaseConnectionFactory.cleanConnection(conn);
		}
		
	}

}
