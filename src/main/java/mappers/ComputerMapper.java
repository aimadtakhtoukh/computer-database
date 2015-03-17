package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import beans.Computer;
import dao.CompanyDAO;
import dao.CompanyDAOImpl;

public class ComputerMapper {
	
	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME = "name";
	private static final String PARAM_INTRODUCED = "introduced";
	private static final String PARAM_DISCONTINUED = "discontinued";
	private static final String PARAM_COMPANY_ID = "company_id";
	
	private static final CompanyDAO companies = CompanyDAOImpl.getInstance();

	public static Computer getMappedResult(ResultSet rs) throws SQLException {
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
			c.setCompany(companies.get(rs.getLong(PARAM_COMPANY_ID)));
			return c;
		}
		return null;
	}
	
	public static List<Computer> getMappedResults(ResultSet rs) throws SQLException {
		List<Computer> list = new LinkedList<Computer>();
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
			c.setCompany(companies.get(rs.getLong(PARAM_COMPANY_ID)));
			list.add(c);
		}
		return list;
	}

}
