package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.beans.Computer;
import com.excilys.mappers.ComputerMapper;

public enum ComputerDAOImpl implements ComputerDAO {
	INSTANCE;
	
	private static final String TABLE_NAME = "computer";
	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME = "name";
	private static final String PARAM_INTRODUCED = "introduced";
	private static final String PARAM_DISCONTINUED = "discontinued";
	private static final String PARAM_COMPANY_ID = "company_id";

	final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	private ComputerDAOImpl() {}
	
	public static ComputerDAOImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public long create(Computer computer) {
		if (computer == null) {
			throw new IllegalArgumentException();
		}
		String insertQuery = new StringBuilder()
						.append("INSERT INTO ")
						.append(TABLE_NAME)
						.append("(")
						.append(PARAM_NAME + ", ")
						.append(PARAM_INTRODUCED + ", ")
						.append(PARAM_DISCONTINUED + ", ")
						.append(PARAM_COMPANY_ID)
						.append(") VALUES (? ,? ,? ,? );")
						.toString();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet key = null;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			ps = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, computer.getName());
			if (computer.getIntroduced() != null) {
				ps.setTimestamp(2, Timestamp.valueOf((computer.getIntroduced())));
			} else {
				ps.setTimestamp(2, null);
			}
			if (computer.getDiscontinued() != null) {
				ps.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
			} else {
				ps.setTimestamp(3, null);
			}
			if (computer.getCompany() != null) {
				ps.setLong(4, computer.getCompany().getId());
			} else {
				ps.setObject(4, null);
			}
			logger.trace("Query : " + ps.toString());
			ps.executeUpdate();
			key = ps.getGeneratedKeys();
			long result = 0L;
			if (key.next()) {
				result =  key.getLong(1);
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterCollection(conn, key, ps);
		}
	}

	@Override
	public long update(long id, Computer computer) {
		if (computer == null) {
			throw new IllegalArgumentException();
		}
		String query = new StringBuilder()
						.append("UPDATE ")
						.append(TABLE_NAME)
						.append(" SET ") 
						.append(PARAM_NAME + "=?, ")
						.append(PARAM_INTRODUCED + "=?, ")
						.append(PARAM_DISCONTINUED + "=?, ")
						.append(PARAM_COMPANY_ID + "=? ")
						.append("WHERE " + PARAM_ID + " = ?")
						.toString();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, computer.getName());
			if (computer.getIntroduced() != null) {
				ps.setTimestamp(2, Timestamp.valueOf((computer.getIntroduced())));
			} else {
				ps.setTimestamp(2, null);
			}
			if (computer.getDiscontinued() != null) {
				ps.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
			} else {
				ps.setTimestamp(3, null);
			}
			if (computer.getCompany() != null) {
				ps.setLong(4, computer.getCompany().getId());
			} else {
				ps.setObject(4, null);
			}
			ps.setLong(5, id);
			logger.trace("Query : " + ps.toString());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			long result = 0L;
			if (rs.next()) {
				result = rs.getLong(1);
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterCollection(conn, rs, ps);
		}
	}

	@Override
	public Computer get(long id) {
		String query = new StringBuilder()
						.append("SELECT * FROM ")
						.append(TABLE_NAME)
						.append(" WHERE ")
						.append(PARAM_ID)
						.append("= ?")
						.toString();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			logger.trace("Query : " + ps.toString());
			rs = ps.executeQuery();
			Computer computer = ComputerMapper.getMappedResult(rs);
			conn.close();
			return computer;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterCollection(conn, rs, ps);
		}
	}

	@Override
	public long delete(long id) {
		String query = new StringBuilder()
						.append("DELETE FROM ")
						.append(TABLE_NAME)
						.append(" WHERE id = ?;")
						.toString();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, id);
			logger.trace("Query : " + ps.toString());
			ps.executeUpdate();
			long result = 0L;
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getLong(1);
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterCollection(conn, rs, ps);
		}
	}

	@Override
	public List<Computer> getAll() {
		String query = new StringBuilder()
						.append("SELECT * FROM ")
						.append(TABLE_NAME)
						.append(";")
						.toString();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			logger.trace("Query : " + stmt.toString());
			rs = stmt.executeQuery(query);
			List<Computer> result = ComputerMapper.getMappedResults(rs);
			conn.close();
			return result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterCollection(conn, rs, stmt);
		}
	}

	@Override
	public List<Computer> getAll(int offset, int limit) {
		String query = "SELECT * FROM " + TABLE_NAME + " LIMIT ? OFFSET ?;";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			logger.trace("Query : " + stmt.toString());
			rs = stmt.executeQuery();
			List<Computer> results = ComputerMapper.getMappedResults(rs);
			conn.close();
			return results;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterCollection(conn, rs, stmt);
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
			logger.trace("Query : " + stmt.toString());
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
			ComputerDatabaseConnectionFactory.cleanAfterCollection(conn, rs, stmt);
		}
	}

}
