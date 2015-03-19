package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import mappers.CompanyMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beans.Company;

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
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public long update(long id, Company company) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public Company get(long id){
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";
		Connection conn;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			logger.trace("Query : " + stmt.toString());
			ResultSet rs = stmt.executeQuery();
			Company company = CompanyMapper.getMappedResult(rs);
			conn.close();
			return company;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public long delete(long id) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<Company> getAll() {
		String query = "SELECT * FROM " + TABLE_NAME + ";";
		Connection conn;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			logger.trace("Query : " + stmt.toString());
			ResultSet rs = stmt.executeQuery(query);
			List<Company> results = CompanyMapper.getMappedResults(rs);
			conn.close();
			return results;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public List<Company> getAll(int offset, int limit) {
		String query = "SELECT * FROM " + TABLE_NAME + " LIMIT ? OFFSET ?;";
		Connection conn;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);
			logger.trace("Query : " + stmt.toString());
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			ResultSet rs = stmt.executeQuery();
			List<Company> results = CompanyMapper.getMappedResults(rs);
			conn.close();
			return results;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public int getCount() {
		String query = "SELECT COUNT(*) FROM " + TABLE_NAME + ";";
		Connection conn;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			logger.trace("Query : " + stmt.toString());
			ResultSet rs = stmt.executeQuery(query);
			int count = -1;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			conn.close();
			return count;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

}
