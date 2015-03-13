package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

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
	public void createComputer(Computer computer) {
		String query = new StringBuilder()
						.append("INSERT INTO ")
						.append(TABLE_NAME)
						.append("(")
						.append(PARAM_NAME + ", ")
						.append(PARAM_INTRODUCED + ", ")
						.append(PARAM_DISCONTINUED + ", ")
						.append(PARAM_COMPANY_ID)
						.append(") VALUES (? ,? ,? ,? );")
						.toString();
		Connection conn;
		try {
			conn = ComputerDatabaseConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
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
			ps.setLong(4, computer.getCompanyId());
			ps.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateComputer(long id, Computer computer) {
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
		Connection conn;
		try {
			conn = ComputerDatabaseConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
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
			ps.setLong(4, computer.getCompanyId());
			ps.setLong(5, id);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Computer getComputer(long id) {
		String query = new StringBuilder()
						.append("SELECT * FROM ")
						.append(TABLE_NAME)
						.append(" WHERE ")
						.append(PARAM_ID)
						.append("= ?")
						.toString();
		Connection conn;
		try {
			conn = ComputerDatabaseConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Computer c = new Computer();
				c.setId(rs.getLong(PARAM_ID));
				c.setName(rs.getString(PARAM_NAME));
				if (rs.getTimestamp(PARAM_INTRODUCED) != null) { 
					c.setIntroduced(rs.getTimestamp(PARAM_INTRODUCED).toLocalDateTime());
				}
				if (rs.getTimestamp(PARAM_DISCONTINUED) != null) {
					c.setDiscontinued(rs.getTimestamp(PARAM_DISCONTINUED).toLocalDateTime());
				}
				c.setCompanyId(rs.getLong(PARAM_COMPANY_ID));
				return c;
			}
			return null;
		} catch (SQLException e) {
			//TODO 
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteComputer(long id) {
		String query = new StringBuilder()
						.append("DELETE FROM ")
						.append(TABLE_NAME)
						.append(" WHERE id = ?;")
						.toString();
		Connection conn;
		try {
			conn = ComputerDatabaseConnectionFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			//TODO
			e.printStackTrace();
		}

	}

	@Override
	public List<Computer> getAllComputers() {
		String query = new StringBuilder()
						.append("SELECT * FROM ")
						.append(TABLE_NAME)
						.append(";")
						.toString();
		Connection conn;
		List<Computer> list = new LinkedList<Computer>();
		try {
			conn = ComputerDatabaseConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Computer c = new Computer();
				c.setId(rs.getLong(PARAM_ID));
				c.setName(rs.getString(PARAM_NAME));
				if (rs.getTimestamp(PARAM_INTRODUCED) != null) { 
					c.setIntroduced(rs.getTimestamp(PARAM_INTRODUCED).toLocalDateTime());
				}
				if (rs.getTimestamp(PARAM_DISCONTINUED) != null) {
					c.setDiscontinued(rs.getTimestamp(PARAM_DISCONTINUED).toLocalDateTime());
				}
				c.setCompanyId(rs.getLong(PARAM_COMPANY_ID));
				list.add(c);
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
