package com.excilys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	private Map<String, String[]> orderByStrings = new HashMap<String, String[]>();

	private final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@PersistenceContext(unitName = "ComputerDatabasePU")
	private EntityManager em;
	@Autowired
	private ComputerDatabaseMapper mapper;
	
	public ComputerDAOImpl() {
        orderByStrings.put("computer.id", new String[] {"id"});
        orderByStrings.put("computer.name", new String[] {"name"});
        orderByStrings.put("computer.introduced", new String[] {"introduced"});
        orderByStrings.put("computer.discontinued", new String[] {"discontinued"});
        orderByStrings.put("company.name", new String[] {"company", "name"});
	}

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
	@Transactional
	public long create(Computer computer) {
		if (computer == null) {
			throw new IllegalArgumentException();
		}
		em.persist(computer);
		return computer.getId();
		/*
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
		return keyHolder.getKey().longValue();arg0
		*/
	}

	@Override
	@Transactional
	public long update(long id, Computer computer) {
		if (computer == null) {
			throw new IllegalArgumentException();
		}
		Computer c = em.find(Computer.class, id);
		c.setId(id);
		c.setName(computer.getName());
		c.setIntroduced(computer.getIntroduced());
		c.setDiscontinued(computer.getDiscontinued());
		c.setCompany(computer.getCompany());
		return c.getId();
		/*
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
		*/
	}

	@Override
	@Transactional
	public long delete(long id) {
		/*
		String query = new StringBuilder()
		.append("DELETE FROM ")
		.append(TABLE_NAME)
		.append(" WHERE id = ?;")
		.toString();

		jdbcTemplate.update(query, id);
		logger.trace("Computer DAO executed the query : " + query);
		*/
		em.remove(em.find(Computer.class, id));
		return id;
	}

	@Override
	public List<Computer> getAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		Root<Computer> from = cq.from(Computer.class);
		cq.select(from);
		TypedQuery<Computer> q = em.createQuery(cq);
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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		Root<Computer> from = cq.from(Computer.class);
		Predicate searchPredicate = cb.and();
		if (searchString != null) {
			if (!searchString.trim().isEmpty()) {
				String searchLikeString = "%" + searchString + "%";
				Predicate searchComputerNamePredicate = cb.like(from.get("name"), searchLikeString);
				Predicate searchCompanyNamePredicate = cb.like(from.get("company").get("name"), searchLikeString);
				searchPredicate = cb.or(searchComputerNamePredicate, searchCompanyNamePredicate);
			}
		}
		cq.where(searchPredicate);
		cq.select(from);
		if (orderBy != null) {
			if (!orderBy.trim().isEmpty()) {
				String[] strings = orderByStrings.get(orderBy);
				if (strings != null) {
					Path<Computer> path = from.get(strings[0]);
					for (int i = 1; i < strings.length; i++) {
						path = path.get(strings[i]);
					}
					Order o;
					if (ascendant) {
						o = cb.asc(path);
					} else {
						o = cb.desc(path);
					}
					cq.orderBy(o);
				}
			}
		}
		TypedQuery<Computer> q = em.createQuery(cq);
		q.setFirstResult(offset);
		q.setMaxResults(limit);
		return q.getResultList();
		/*
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		Root<Computer> from = cq.from(Computer.class);
		cq.select(from);
		TypedQuery<Computer> q = em.createQuery(cq);
		return q.getResultList();
		*/
		/*
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
		List<Object> params = new ArrayList<Object>();
		if (hasASearchString) {
			params.add("%" + searchString + "%");
			params.add("%" + searchString + "%");
		}
		params.add(limit);
		params.add(offset);
		logger.trace("Computer DAO executed the query : " + query);
		return jdbcTemplate.query(query, params.toArray(), mapper);
		*/
	}

	@Override
	public int getCount() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Computer.class)));
		return em.createQuery(cq).getSingleResult().intValue();
		/*
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) FROM ");
		sb.append(TABLE_NAME);
		sb.append(";");
		String query =  sb.toString();
		logger.trace("Company DAO executed the query : " + query);		
		return jdbcTemplate.queryForObject(query, Integer.class);
		*/
	}

	@Override
	public void deleteByCompanyId(long companyId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<Computer> delete = cb.createCriteriaDelete(Computer.class);
		Root<Computer> from = delete.from(Computer.class);
		delete.where(cb.equal(from.get("company").get("id"), companyId));
		em.createQuery(delete).executeUpdate();
		/*
		String query = new StringBuilder()
		.append("DELETE FROM ")
		.append(TABLE_NAME)
		.append(" WHERE ")
		.append(PARAM_COMPANY_ID)
		.append(" = ?;")
		.toString();
		logger.trace("Computer DAO executed the query : " + query);
		jdbcTemplate.update(query, companyId);
		*/
	}
}
