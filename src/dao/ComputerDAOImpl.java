package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import beans.Computer;

public enum ComputerDAOImpl implements ComputerDAO {
	INSTANCE;
	

	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME= "name";
	private static final String PARAM_INTRODUCED = "introduced";
	private static final String PARAM_DISCONTINUED = "discontinued";
	private static final String PARAM_COMPANY_ID = "company_id";

	private ComputerDAOImpl() {}
	
	public static ComputerDAOImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public void createComputer(Computer computer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateComputer(long id, Computer computer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Computer getComputer(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteComputer(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Computer> getAllComputers() {
		String query = "SELECT * FROM computer;";
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
				try {
					c.setIntroduced(rs.getDate(PARAM_INTRODUCED));
				} catch (SQLException e) {
					c.setIntroduced(null);
				}
				try {
					c.setDiscontinued(rs.getDate(PARAM_DISCONTINUED));
				} catch (SQLException e) {
					c.setDiscontinued(null);
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
