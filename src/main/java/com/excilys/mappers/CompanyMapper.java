package com.excilys.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.beans.Company;

@Component
public class CompanyMapper implements RowMapper<Company>{

	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME = "name";
	/*
	public Company getMappedResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			return new Company(rs.getLong(PARAM_ID), rs.getString(PARAM_NAME));
		}
		return null;
	}
	
	public List<Company> getMappedResults(ResultSet rs) throws SQLException {
		List<Company> list = new LinkedList<>();
		while(rs.next()) {
			list.add(new Company(rs.getLong(PARAM_ID), rs.getString(PARAM_NAME)));
		}
		return list;
	}
	*/
	
	@Override
	public Company mapRow(ResultSet rs, int rowNumber) throws SQLException {
		return new Company(rs.getLong(PARAM_ID), rs.getString(PARAM_NAME));
	}

}
