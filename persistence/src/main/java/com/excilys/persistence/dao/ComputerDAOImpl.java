package com.excilys.persistence.dao;

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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.core.beans.Computer;
import com.excilys.persistence.mappers.ComputerDatabaseMapper;

@Repository
public class ComputerDAOImpl implements ComputerDAO {
	
	private Map<String, String[]> orderByStrings = new HashMap<String, String[]>();

	private final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
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
		logger.trace("ComputerDAO doit récupérer le Computer d'id " + id);
		return em.find(Computer.class, id);		
	}

	@Override
	@Transactional
	public long create(Computer computer) {
		logger.trace("ComputerDAO ajoute en base le Computer " + computer);
		if (computer == null) {
			throw new IllegalArgumentException();
		}
		em.persist(computer);
		return computer.getId();
	}

	@Override
	@Transactional
	public long update(long id, Computer computer) {
		logger.trace("ComputerDAO met à jour le Computer " + id + "avec les données : " + computer);
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
	}

	@Override
	@Transactional
	public long delete(long id) {
		logger.trace("ComputerDAO supprime le Computer d'id " + id);
		em.remove(em.find(Computer.class, id));
		return id;
	}

	@Override
	public List<Computer> getAll() {
		logger.trace("ComputerDAO recupère tous les Computers.");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		Root<Computer> from = cq.from(Computer.class);
		cq.select(from);
		TypedQuery<Computer> q = em.createQuery(cq);
		return q.getResultList();
	}

	@Override
	public List<Computer> getAll(int offset, int limit, String orderBy, boolean ascendant, String searchString) {
		logger.trace("ComputerDAO récupère tous les Computers de " + offset + " à " + (offset + limit) + ".");
		logger.trace("OrderBy : " + orderBy);
		logger.trace("Ascendant : " + ascendant);
		logger.trace("Search String : " + searchString);
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
	}

	@Override
	public int getCount() {
		logger.trace("ComputerDAO renvoie le nombre de Computers.");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Computer.class)));
		return em.createQuery(cq).getSingleResult().intValue();
	}

	@Override
	public void deleteByCompanyId(long companyId) {
		logger.trace("ComputerDAO supprime tous les Computers de companyId " + companyId);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<Computer> delete = cb.createCriteriaDelete(Computer.class);
		Root<Computer> from = delete.from(Computer.class);
		delete.where(cb.equal(from.get("company").get("id"), companyId));
		em.createQuery(delete).executeUpdate();
	}
}
