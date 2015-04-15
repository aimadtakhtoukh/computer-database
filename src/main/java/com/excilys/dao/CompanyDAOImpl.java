package com.excilys.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Company;
import com.excilys.mappers.CompanyDatabaseMapper;

@Repository
public class CompanyDAOImpl implements CompanyDAO {
	
	private static final String TABLE_NAME = "company";
	
	final Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CompanyDatabaseMapper mapper;

	@Override
	public Company get(long id) {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";
		List<Company> result = jdbcTemplate.query(query, new Object[] {id} , mapper);
		logger.trace("Company DAO executed the query : " + query);
		return result.isEmpty() ? null : result.get(0);
	}
	
	@Override
	public List<Company> getAll(int offset, int limit, String orderBy, boolean ascendant, String searchString) {
		StringBuilder queryBuilder = new StringBuilder();
		boolean hasASearchString = false;
		queryBuilder
			.append("SELECT * FROM ")
			.append(TABLE_NAME);
		if (searchString != null) {
			if (!searchString.trim().isEmpty()) {
				hasASearchString = true;
				queryBuilder.append(" WHERE company.")
					.append("name")
					.append(" LIKE % ? %")
				;
			}
		}
		if (orderBy != null) {
			if (!orderBy.trim().isEmpty()) {
				queryBuilder.append(" ORDER BY ");
				queryBuilder.append("if(" + orderBy + " is null, 1 , 0), ");
				queryBuilder.append(orderBy);
				if (!ascendant) {
					queryBuilder.append(" DESC");
				}
			}
		}
		queryBuilder.append(" LIMIT ? OFFSET ?;");
		String query = queryBuilder.toString();
		logger.trace("Computer DAO executed the query : " + query);
		List<Object> params = new ArrayList<Object>();
		if (hasASearchString) {
			params.add("%" + searchString + "%");
			params.add("%" + searchString + "%");
		}
		params.add(limit);
		params.add(offset);
		return jdbcTemplate.query(query, params.toArray() , mapper);
	}

	@Override
	public long delete(long id) {
		String query = new StringBuilder()
		.append("DELETE FROM ")
		.append(TABLE_NAME)
		.append(" WHERE id = ?;")
		.toString();
		jdbcTemplate.update(query, id);
		logger.trace("Company DAO executed the query : " + query);
		return id;
	}

	@Override
	public List<Company> getAll() {
		String query = "SELECT * FROM " + TABLE_NAME + ";";
		logger.trace("Company DAO executed the query : " + query);
		return jdbcTemplate.query(query , mapper);
	}

	@Override
	public int getCount() {
		String query = "SELECT COUNT(*) FROM " + TABLE_NAME + ";";
		logger.trace("Company DAO executed the query : " + query);		
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
}
