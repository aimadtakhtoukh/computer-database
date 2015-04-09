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
	@Autowired
	private CompanyMapper mapper;

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
			company = mapper.getMappedResult(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.releaseRessources(rs, stmt);
		}
		return company;
	}
	
	@Override
	public List<Company> getAll(int offset, int limit, String orderBy, boolean ascendant, String searchString) {
		Connection conn = cdcf.getConnection();
		StringBuilder queryBuilder = new StringBuilder();
		boolean hasASearchString = false;
		queryBuilder
			.append("SELECT * FROM ")
			.append(TABLE_NAME);
		if (searchString != null) {
			if (!searchString.trim().isEmpty()) {
				hasASearchString = true;
				queryBuilder.append(" WHERE company.")
					.append("name")
					.append(" LIKE % ? %")
				;
			}
		}
		if (orderBy != null) {
			if (!orderBy.trim().isEmpty()) {
				queryBuilder.append(" ORDER BY ");
				queryBuilder.append("if(" + orderBy + " is null, 1 , 0), ");
				queryBuilder.append(orderBy);
				if (!ascendant) {
					queryBuilder.append(" DESC");
				}
			}
		}
		queryBuilder.append(" LIMIT ? OFFSET ?;");
		String query = queryBuilder.toString();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			int index = 0;
			stmt = conn.prepareStatement(query);
			if (hasASearchString) {
				stmt.setString(++index, "%" + searchString + "%");
				stmt.setString(++index, "%" + searchString + "%");
			}
			stmt.setInt(++index, limit);
			stmt.setInt(++index, offset);
			logger.trace("Computer DAO executed the query : " + stmt.toString());
			rs = stmt.executeQuery();
			List<Company> results = mapper.getMappedResults(rs);
			return results;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.releaseRessources(rs, stmt);
		}
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
			List<Company> results = mapper.getMappedResults(rs);
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
			return count;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.releaseRessources(rs, stmt);
		}
	}
}
