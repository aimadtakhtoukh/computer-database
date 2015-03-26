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
	public Company get(long id) {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		Company result = get(id, conn);
		ComputerDatabaseConnectionFactory.cleanConnection(conn);
		return result;
	}

	@Override
	public long delete(long id) {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		long result = delete(id, conn);
		ComputerDatabaseConnectionFactory.cleanConnection(conn);
		return result;
	}

	@Override
	public List<Company> getAll() {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		List<Company> result = getAll(conn);
		ComputerDatabaseConnectionFactory.cleanConnection(conn);
		return result;
	}

	@Override
	public List<Company> getAll(int offset, int limit) {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		List<Company> result = getAll(offset, limit, conn);
		ComputerDatabaseConnectionFactory.cleanConnection(conn);
		return result;
	}

	@Override
	public int getCount() {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		int result = getCount(conn);
		ComputerDatabaseConnectionFactory.cleanConnection(conn);
		return result;
	}

	@Override
	public Company get(long id, Connection conn) {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";
		Company company = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			logger.trace("Company DAO executed the query : " + stmt.toString());
			rs = stmt.executeQuery();
			company = CompanyMapper.getMappedResult(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(rs, stmt);
		}
		return company;
	}

	@Override
	public long delete(long id, Connection conn) {
		String query = new StringBuilder()
		.append("DELETE FROM ")
		.append(TABLE_NAME)
		.append(" WHERE id = ?;")
		.toString();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, id);
			logger.trace("Company DAO executed the query : " + ps.toString());
			ps.executeUpdate();
			long result = 0L;
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getLong(1);
			}
			return result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(rs, ps);
		}
	}

	@Override
	public List<Company> getAll(Connection conn) {
		String query = "SELECT * FROM " + TABLE_NAME + ";";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			logger.trace("Company DAO executed the query : " + stmt.toString());
			rs = stmt.executeQuery(query);
			List<Company> results = CompanyMapper.getMappedResults(rs);
			conn.close();
			return results;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(rs, stmt);
		}
	}

	@Override
	public List<Company> getAll(int offset, int limit, Connection conn) {
		String query = "SELECT * FROM " + TABLE_NAME + " LIMIT ? OFFSET ?;";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
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
			ComputerDatabaseConnectionFactory.cleanAfterConnection(rs, stmt);
		}
	}

	@Override
	public int getCount(Connection conn) {
		String query = "SELECT COUNT(*) FROM " + TABLE_NAME + ";";
		Statement stmt = null;
		ResultSet rs = null;
		try {
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
			ComputerDatabaseConnectionFactory.cleanAfterConnection(rs, stmt);
		}
	}

}
