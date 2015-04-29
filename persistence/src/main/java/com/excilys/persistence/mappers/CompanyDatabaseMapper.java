package com.excilys.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.core.beans.Company;

@Component
public class CompanyDatabaseMapper implements RowMapper<Company>{

	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME = "name";
	
	@Override
	public Company mapRow(ResultSet rs, int rowNumber) throws SQLException {
		return Company.builder().id(rs.getLong(PARAM_ID)).name(rs.getString(PARAM_NAME)).build();
	}

}
