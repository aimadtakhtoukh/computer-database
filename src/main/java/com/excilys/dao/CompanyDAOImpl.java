package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.beans.Company;
import com.excilys.mappers.CompanyMapper;

public enum CompanyDAOImpl implements CompanyDAO {
	INSTANCE;
	
	private static final String TABLE_NAME = "company";
	
	final Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	private CompanyDAOImpl() {}
	
	public static CompanyDAOImpl getInstance() {
		return INSTANCE;
	}	

	@Override
	public long create(Company company) {
		logger.error("Company DAO has been asked to create a company. Not implemented.");
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public long update(long id, Company company) {
		logger.error("Company DAO has been asked to update a company. Not implemented.");
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public Company get(long id){
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";
		Connection conn = null;
		Company company = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			logger.trace("Company DAO executed the query : " + stmt.toString());
			rs = stmt.executeQuery();
			company = CompanyMapper.getMappedResult(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(conn, rs, stmt);
		}
		return company;
	}

	@Override
	public long delete(long id) {
		logger.error("Company DAO has been asked to delete a company. Not implemented.");
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<Company> getAll() {
		String query = "SELECT * FROM " + TABLE_NAME + ";";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			logger.trace("Company DAO executed the query : " + stmt.toString());
			rs = stmt.executeQuery(query);
			List<Company> results = CompanyMapper.getMappedResults(rs);
			conn.close();
			return results;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(conn, rs, stmt);
		}
	}

	@Override
	public List<Company> getAll(int offset, int limit) {
		String query = "SELECT * FROM " + TABLE_NAME + " LIMIT ? OFFSET ?;";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			stmt = conn.prepareStatement(query);
			logger.trace("Company DAO executed the query : " + stmt.toString());
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			rs = stmt.executeQuery();
			List<Company> results = CompanyMapper.getMappedResults(rs);
			conn.close();
			return results;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(conn, rs, stmt);
		}
	}

	@Override
	public int getCount() {
		String query = "SELECT COUNT(*) FROM " + TABLE_NAME + ";";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			logger.trace("Company DAO executed the query : " + stmt.toString());
			rs = stmt.executeQuery(query);
			int count = -1;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			conn.close();
			return count;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(conn, rs, stmt);
		}
	}

}
