package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import mappers.ComputerMapper;
import beans.Computer;

public enum ComputerDAOImpl implements ComputerDAO {
	INSTANCE;
	
	private static final String TABLE_NAME = "computer";
	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME = "name";
	private static final String PARAM_INTRODUCED = "introduced";
	private static final String PARAM_DISCONTINUED = "discontinued";
	private static final String PARAM_COMPANY_ID = "company_id";

	private ComputerDAOImpl() {}
	
	public static ComputerDAOImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public long createComputer(Computer computer) throws SQLException {
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
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
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
		ps.setLong(4, computer.getCompany().getId());
		ps.executeUpdate();
		ResultSet key = ps.getGeneratedKeys();
		long result = 0L;
		if (key.next()) {
			result =  key.getLong(1);
		}
		conn.close();
		return result;
	}

	@Override
	public long updateComputer(long id, Computer computer) throws SQLException {
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
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
			ps.setLong(4, 0);
		}
		ps.setLong(5, id);

		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		long result = 0L;
		if (rs.next()) {
			result = rs.getLong(1);
		}
		conn.close();
		return result;
	}

	@Override
	public Computer getComputer(long id) throws SQLException {
		String query = new StringBuilder()
						.append("SELECT * FROM ")
						.append(TABLE_NAME)
						.append(" WHERE ")
						.append(PARAM_ID)
						.append("= ?")
						.toString();
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, id);
		Computer computer = ComputerMapper.getMappedResult(ps.executeQuery());
		conn.close();
		return computer;
	}

	@Override
	public long deleteComputer(long id) throws SQLException {
		String query = new StringBuilder()
						.append("DELETE FROM ")
						.append(TABLE_NAME)
						.append(" WHERE id = ?;")
						.toString();
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, id);
		ps.executeUpdate();
		long result = 0L;
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			result = rs.getLong(1);
		}
		conn.close();
		return result;
	}

	@Override
	public List<Computer> getAllComputers() throws SQLException {
		String query = new StringBuilder()
						.append("SELECT * FROM ")
						.append(TABLE_NAME)
						.append(";")
						.toString();
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		List<Computer> result = ComputerMapper.getMappedResults(rs);
		conn.close();
		return result;
	}

}
