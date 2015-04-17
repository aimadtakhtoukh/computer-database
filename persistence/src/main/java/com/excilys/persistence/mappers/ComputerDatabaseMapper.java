package com.excilys.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.core.beans.Computer;
import com.excilys.persistence.dao.CompanyDAO;

@Component
public class ComputerDatabaseMapper implements RowMapper<Computer>{
	
	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME = "name";
	private static final String PARAM_INTRODUCED = "introduced";
	private static final String PARAM_DISCONTINUED = "discontinued";
	private static final String PARAM_COMPANY_ID = "company_id";
	
	@Autowired
	private CompanyDAO companies;
	
	@Override
	public Computer mapRow(ResultSet rs, int rownumber) throws SQLException {
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

}
