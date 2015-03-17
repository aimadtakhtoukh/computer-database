package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import mappers.CompanyMapper;
import beans.Company;

public enum CompanyDAOImpl implements CompanyDAO {
	INSTANCE;
	
	private CompanyDAOImpl() {}
	
	public static CompanyDAOImpl getInstance() {
		return INSTANCE;
	}	

	@Override
	public long createCompany(Company company) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public long updateCompany(long id, Company company) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public Company getCompany(long id) throws SQLException {
		String query = "SELECT * FROM company WHERE id = ?;";
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		Company company = CompanyMapper.getMappedResult(rs);
		conn.close();
		return company;
	}

	@Override
	public long deleteCompany(long id) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<Company> getAllCompanies() throws SQLException {
		String query = "SELECT * FROM company;";
		Connection conn = ComputerDatabaseConnectionFactory.getInstance().getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		List<Company> results = CompanyMapper.getMappedResults(rs);
		conn.close();
		return results;
	}

}
