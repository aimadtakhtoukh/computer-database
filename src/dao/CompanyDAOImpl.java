package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import beans.Company;

public enum CompanyDAOImpl implements CompanyDAO {
	INSTANCE;	

	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME = "name";
	
	private CompanyDAOImpl() {}
	
	public static CompanyDAOImpl getInstance() {
		return INSTANCE;
	}	

	@Override
	public void createCompany(Company company) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void updateCompany(long id, Company company) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public Company getCompany(long id) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void deleteCompany(long id) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<Company> getAllCompanies() {
		String query = "SELECT * FROM company;";
		Connection conn;
		List<Company> list = new LinkedList<Company>();
		try {
			conn = ComputerDatabaseConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Company c = new Company();
				c.setId(rs.getLong(PARAM_ID));
				c.setName(rs.getString(PARAM_NAME));
				list.add(c);
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			return null;
		}
	}

}
