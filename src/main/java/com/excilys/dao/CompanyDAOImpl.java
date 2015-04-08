package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Company;
import com.excilys.mappers.CompanyMapper;

@Repository
public class CompanyDAOImpl implements CompanyDAO {
	
	private static final String TABLE_NAME = "company";
	
	final Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	@Autowired
	private ComputerDatabaseConnectionFactory cdcf;

	@Override
	public Company get(long id) {
		Connection conn = cdcf.getConnection();
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
			ComputerDatabaseConnectionFactory.releaseRessources(rs, stmt);
		}
		return company;
	}

	@Override
	public long delete(long id) {
		Connection conn = cdcf.getConnection();
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
			ComputerDatabaseConnectionFactory.releaseRessources(rs, ps);
		}
	}

	@Override
	public List<Company> getAll() {
		Connection conn = cdcf.getConnection();
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
			ComputerDatabaseConnectionFactory.releaseRessources(rs, stmt);
		}
	}

	@Override
	public int getCount() {
		Connection conn = cdcf.getConnection();
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
			ComputerDatabaseConnectionFactory.releaseRessources(rs, stmt);
		}
	}
}
