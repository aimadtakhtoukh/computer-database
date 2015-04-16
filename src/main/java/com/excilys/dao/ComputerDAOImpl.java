package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.beans.Computer;
import com.excilys.mappers.ComputerDatabaseMapper;

@Repository
public class ComputerDAOImpl implements ComputerDAO {
	
	private static final String TABLE_NAME = "computer";
	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME = "name";
	private static final String PARAM_INTRODUCED = "introduced";
	private static final String PARAM_DISCONTINUED = "discontinued";
	private static final String PARAM_COMPANY_ID = "company_id";

	private final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@PersistenceContext(unitName = "ComputerDatabasePU")
	private EntityManager em;
	@Autowired
	private ComputerDatabaseMapper mapper;

	@Override
	public Computer get(long id) {
		return em.find(Computer.class, id);
		/*
		String query = new StringBuilder()
			.append("SELECT * FROM ")
			.append(TABLE_NAME)
			.append(" WHERE ")
			.append(PARAM_ID)
			.append("= ?")
			.toString();
		List<Computer> result = jdbcTemplate.query(query, new Object[] {id} , mapper);
		logger.trace("Computer DAO executed the query : " + query);
		return result.isEmpty() ? null : result.get(0);
		*/
		
	}

	@Override
	public long create(Computer computer) {
		if (computer == null) {
			throw new IllegalArgumentException();
		}
		String query = new StringBuilder()
						.append("INSERT INTO ")
						.append(TABLE_NAME)
						.append("(")
						.append(PARAM_NAME + ", ")
						.append(PARAM_INTRODUCED + ", ")
						.append(PARAM_DISCONTINUED + ", ")
						.append(PARAM_COMPANY_ID)
						.append(") VALUES (? ,? ,? ,? );")
						.toString();
		final Timestamp introduced;
		final Timestamp discontinued;
		if (computer.getIntroduced() != null) {
			introduced = Timestamp.valueOf(computer.getIntroduced());
		} else {
			introduced = null;
		}
		if (computer.getDiscontinued() != null) {
			discontinued = Timestamp.valueOf(computer.getDiscontinued());
		} else {
			discontinued = null;
		}
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps =
								connection.prepareStatement(query, 
										Statement.RETURN_GENERATED_KEYS);
						int paramIndex = 0;
						ps.setString(++paramIndex, computer.getName());
						ps.setTimestamp(++paramIndex, introduced);
						ps.setTimestamp(++paramIndex, discontinued);
						if (computer.getCompany() != null) {
							ps.setLong(++paramIndex, computer.getCompany().getId());
						} else {
							ps.setNull(++paramIndex, Types.BIGINT);
						}
						return ps;
					}
				},
				keyHolder);
		logger.trace("Computer DAO executed the query : " + query);
		return keyHolder.getKey().longValue();
	}

	@Override
	public long update(long id, Computer computer) {
		if (computer == null) {
			throw new IllegalArgumentException();
		}
		String query = new StringBuilder()
						.append("UPDATE ")
						.append(TABLE_NAME)
						.append(" SET ") 
						.append(PARAM_NAME + "=?, ")
						.append(PARAM_INTRODUCED + "=?, ")
						.append(PARAM_DISCONTINUED + "=?, ")
						.append(PARAM_COMPANY_ID + "=? ")
						.append("WHERE " + PARAM_ID + " = ?")
						.toString();
		final Timestamp introduced;
		final Timestamp discontinued;
		if (computer.getIntroduced() != null) {
			introduced = Timestamp.valueOf(computer.getIntroduced());
		} else {
			introduced = null;
		}
		if (computer.getDiscontinued() != null) {
			discontinued = Timestamp.valueOf(computer.getDiscontinued());
		} else {
			discontinued = null;
		}
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps =
								connection.prepareStatement(query, 
										Statement.RETURN_GENERATED_KEYS);
						int paramIndex = 0;
						ps.setString(++paramIndex, computer.getName());
						ps.setTimestamp(++paramIndex, introduced);
						ps.setTimestamp(++paramIndex, discontinued);
						if (computer.getCompany() != null) {
							ps.setLong(++paramIndex, computer.getCompany().getId());
						} else {
							ps.setNull(++paramIndex, Types.BIGINT);
						}
						ps.setLong(++paramIndex, id);
						return ps;
					}
				},
				keyHolder);
		logger.trace("Computer DAO executed the query : " + query);
		if (keyHolder.getKey() != null) {
			return keyHolder.getKey().longValue();
		} else {
			return 0;
		}
	}

	@Override
	public long delete(long id) {
		String query = new StringBuilder()
		.append("DELETE FROM ")
		.append(TABLE_NAME)
		.append(" WHERE id = ?;")
		.toString();

		jdbcTemplate.update(query, id);
		logger.trace("Computer DAO executed the query : " + query);
		return id;
	}

	@Override
	public List<Computer> getAll() {
		logger.trace("Creating Criteria Builder...");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		logger.trace("Creating Criteria Query...");
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		logger.trace("Creating Criteria Root...");
		Root<Computer> from = cq.from(Computer.class);
		logger.trace("Selecting...");
		cq.select(from);
		logger.trace("Creating Typed Query...");
		TypedQuery<Computer> q = em.createQuery(cq);
		logger.trace("Creating Result list...");
		return q.getResultList();
		/*
		String query = new StringBuilder()
		.append("SELECT * FROM ")
		.append(TABLE_NAME)
		.append(";")
		.toString();
		logger.trace("Computer DAO executed the query : " + query);
		return jdbcTemplate.query(query , mapper);
		*/
	}

	@Override
	public List<Computer> getAll(int offset, int limit, String orderBy, boolean ascendant, String searchString) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder
			.append("SELECT * FROM ")
			.append(TABLE_NAME)
			.append(" LEFT JOIN company ON ")
			.append("computer.company_id = company.id");
		
		boolean hasASearchString = false;
		if (searchString != null) {
			if (!searchString.trim().isEmpty()) {
				hasASearchString = true;
				queryBuilder.append(" WHERE computer.")
					.append(PARAM_NAME)
					.append(" LIKE ?")
					.append(" OR company.name LIKE ?");
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
		return jdbcTemplate.query(query, params.toArray(), mapper);
	}

	@Override
	public int getCount() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) FROM ");
		sb.append(TABLE_NAME);
		sb.append(";");
		String query =  sb.toString();
		logger.trace("Company DAO executed the query : " + query);		
		return jdbcTemplate.queryForObject(query, Integer.class);
	}

	@Override
	public void deleteByCompanyId(long companyId) {
		String query = new StringBuilder()
		.append("DELETE FROM ")
		.append(TABLE_NAME)
		.append(" WHERE ")
		.append(PARAM_COMPANY_ID)
		.append(" = ?;")
		.toString();
		logger.trace("Computer DAO executed the query : " + query);
		jdbcTemplate.update(query, companyId);
	}
}
