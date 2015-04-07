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
	public Computer get(long id) {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		String query = new StringBuilder()
			.append("SELECT * FROM ")
			.append(TABLE_NAME)
			.append(" WHERE ")
			.append(PARAM_ID)
			.append("= ?")
			.toString();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			logger.trace("Computer DAO executed the query : " + ps.toString());
			rs = ps.executeQuery();
			Computer computer = ComputerMapper.getMappedResult(rs);
			return computer;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(rs, ps);
			ComputerDatabaseConnectionFactory.cleanConnection(conn);
		}
	}

	@Override
	public long create(Computer computer) {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		long result = create(computer, conn);
		ComputerDatabaseConnectionFactory.cleanConnection(conn);
		return result;
	}

	@Override
	public long update(long id, Computer computer) {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		long result = update(id, computer, conn);
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
	public List<Computer> getAll() {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		String query = new StringBuilder()
		.append("SELECT * FROM ")
		.append(TABLE_NAME)
		.append(";")
		.toString();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			logger.trace("Computer DAO executed the query : " + stmt.toString());
			rs = stmt.executeQuery(query);
			List<Computer> result = ComputerMapper.getMappedResults(rs);
			return result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(rs, stmt);
			ComputerDatabaseConnectionFactory.cleanConnection(conn);
		}
	}

	@Override
	public List<Computer> getAll(int offset, int limit, String orderBy, boolean ascendant, String searchString) {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		StringBuilder queryBuilder = new StringBuilder();
		boolean hasASearchString = false;
		queryBuilder
			.append("SELECT * FROM ")
			.append(TABLE_NAME)
			.append(" LEFT JOIN company ON ")
			.append("computer.company_id = company.id");
		if (searchString != null) {
			if (!searchString.trim().isEmpty()) {
				hasASearchString = true;
				queryBuilder.append(" WHERE company.")
					.append(PARAM_NAME)
					.append(" LIKE ?")
					.append(" OR company.name LIKE ?");
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
			List<Computer> results = ComputerMapper.getMappedResults(rs);
			return results;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(rs, stmt);
			ComputerDatabaseConnectionFactory.cleanConnection(conn);
		}
	}

	@Override
	public int getCount() {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		String query = "SELECT COUNT(*) FROM " + TABLE_NAME + ";";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			logger.trace("Computer DAO executed the query : " + stmt.toString());
			rs = stmt.executeQuery(query);
			int count = -1;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(rs, stmt);
			ComputerDatabaseConnectionFactory.cleanConnection(conn);
		}
	}

	@Override
	public long create(Computer computer, Connection conn) {
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
		PreparedStatement ps = null;
		ResultSet key = null;
		try {
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
			logger.trace("Computer DAO executed the query : " + ps.toString());
			ps.executeUpdate();
			key = ps.getGeneratedKeys();
			long result = 0L;
			if (key.next()) {
				result =  key.getLong(1);
			}
			return result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(key, ps);
		}
	}

	@Override
	public long update(long id, Computer computer, Connection conn) {
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
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
			logger.trace("Computer DAO executed the query : " + ps.toString());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			long result = 0L;
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
			logger.trace("Computer DAO executed the query : " + ps.toString());
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
	public void deleteByCompanyId(long companyId) {
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		delete(companyId, conn);
		ComputerDatabaseConnectionFactory.cleanConnection(conn);
	}

	@Override
	public void deleteByCompanyId(long companyId, Connection conn) {
		String query = new StringBuilder()
			.append("DELETE FROM ")
			.append(TABLE_NAME)
			.append(" WHERE ")
			.append(PARAM_COMPANY_ID)
			.append(" = ?;")
			.toString();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setLong(1, companyId);
			logger.trace("Computer DAO executed the query : " + ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ComputerDatabaseConnectionFactory.cleanAfterConnection(null, ps);
		}
	}
}
