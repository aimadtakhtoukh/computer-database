package com.excilys.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.CompanyDAOImpl;

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
			Long companyId = rs.getLong(PARAM_COMPANY_ID);
			if (companyId != 0) {
				c.setCompany(companies.get(companyId));
			} else {
				c.setCompany(null);
			}
			return c;
		}
		return null;
	}
	
	public static List<Computer> getMappedResults(ResultSet rs) throws SQLException {
		List<Computer> list = new LinkedList<Computer>();
		Map<Long, Company> cache = new HashMap<Long, Company>();
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
			Long companyId = rs.getLong(PARAM_COMPANY_ID);
			if (companyId != 0) {
				if (cache.get(companyId) == null) {
					cache.put(companyId, companies.get(companyId));
				}
				c.setCompany(cache.get(companyId));
			} else {
				c.setCompany(null);
			}
			list.add(c);
		}
		return list;
	}

}
