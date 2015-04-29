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
		Computer.Builder builder =  Computer.builder()
				.id(rs.getLong(PARAM_ID))
				.name(rs.getString(PARAM_NAME));
		if (rs.getTimestamp(PARAM_INTRODUCED) != null) { 
			builder.introduced(rs.getTimestamp(PARAM_INTRODUCED).toLocalDateTime());
		}
		if (rs.getTimestamp(PARAM_DISCONTINUED) != null) {
			builder.discontinued(rs.getTimestamp(PARAM_DISCONTINUED).toLocalDateTime());
		}
		Long companyId = rs.getLong(PARAM_COMPANY_ID);
		if (companyId != 0) {
			builder.company(companies.get(companyId));
		}			
		return builder.build();
	}

}
