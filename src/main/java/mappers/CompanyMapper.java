package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import beans.Company;

public class CompanyMapper {

	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME = "name";
	
	public static Company getMappedResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			return new Company(rs.getLong(PARAM_ID), rs.getString(PARAM_NAME));
		}
		return null;
	}
	
	public static List<Company> getMappedResults(ResultSet rs) throws SQLException {
		List<Company> list = new LinkedList<Company>();
		while(rs.next()) {
			list.add(new Company(rs.getLong(PARAM_ID), rs.getString(PARAM_NAME)));
		}
		return list;
	}

}
