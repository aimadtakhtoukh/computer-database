package com.excilys.persistence.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.core.beans.Computer;
import com.excilys.persistence.converter.CompanyEntityConverter;
import com.excilys.persistence.converter.ComputerEntityConverter;
import com.excilys.persistence.entity.ComputerEntity;

@Repository
public class ComputerDAOImpl implements ComputerDAO {
	
	private Map<String, String[]> orderByStrings = new HashMap<String, String[]>();

	private final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	@PersistenceContext(unitName = "ComputerDatabasePU")
	private EntityManager em;
	
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
		return ComputerEntityConverter.to(em.find(ComputerEntity.class, id));		
	}

	@Override
	@Transactional
	public long create(Computer computer) {
		logger.trace("ComputerDAO ajoute en base le Computer " + computer);
		if (computer == null) {
			throw new IllegalArgumentException();
		}
		ComputerEntity entity = ComputerEntityConverter.from(computer);
		em.persist(entity);
		return entity.getId();
	}

	@Override
	@Transactional
	public long update(long id, Computer computer) {
		logger.trace("ComputerDAO met à jour le Computer " + id + "avec les données : " + computer);
		if (computer == null) {
			throw new IllegalArgumentException();
		}
		ComputerEntity c = em.find(ComputerEntity.class, id);
		c.setId(id);
		c.setName(computer.getName());
		if (computer.getIntroduced() != null) {
			c.setIntroduced(Timestamp.valueOf(computer.getIntroduced()));
		} else {
			c.setIntroduced(null);
		}
		if (computer.getDiscontinued() != null) {
			c.setDiscontinued(Timestamp.valueOf(computer.getDiscontinued()));
		} else {
			c.setDiscontinued(null);
		}
		if (computer.getCompany() != null) {
			c.setCompanyEntity(CompanyEntityConverter.from(computer.getCompany()));
		} else {
			c.setCompanyEntity(null);
		}
		return c.getId();
	}

	@Override
	@Transactional
	public long delete(long id) {
		logger.trace("ComputerDAO supprime le Computer d'id " + id);
		em.remove(em.find(ComputerEntity.class, id));
		return id;
	}

	@Override
	public List<Computer> getAll() {
		logger.trace("ComputerDAO recupère tous les Computers.");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ComputerEntity> cq = cb.createQuery(ComputerEntity.class);
		Root<ComputerEntity> from = cq.from(ComputerEntity.class);
		cq.select(from);
		TypedQuery<ComputerEntity> q = em.createQuery(cq);
		return q.getResultList().stream().map(ComputerEntityConverter::to).collect(Collectors.toList());
	}

	@Override
	public List<Computer> getAll(int offset, int limit, String orderBy, boolean ascendant, String searchString) {
		logger.trace("ComputerDAO récupère tous les Computers de " + offset + " à " + (offset + limit) + ".");
		logger.trace("OrderBy : " + orderBy);
		logger.trace("Ascendant : " + ascendant);
		logger.trace("Search String : " + searchString);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ComputerEntity> cq = cb.createQuery(ComputerEntity.class);
		Root<ComputerEntity> from = cq.from(ComputerEntity.class);
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
		TypedQuery<ComputerEntity> q = em.createQuery(cq);
		q.setFirstResult(offset);
		q.setMaxResults(limit);
		return q.getResultList().stream().map(ComputerEntityConverter::to).collect(Collectors.toList());
	}

	@Override
	public int getCount() {
		logger.trace("ComputerDAO renvoie le nombre de Computers.");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(ComputerEntity.class)));
		return em.createQuery(cq).getSingleResult().intValue();
	}

	@Override
	public void deleteByCompanyId(long companyId) {
		logger.trace("ComputerDAO supprime tous les Computers de companyId " + companyId);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<ComputerEntity> delete = cb.createCriteriaDelete(ComputerEntity.class);
		Root<ComputerEntity> from = delete.from(ComputerEntity.class);
		delete.where(cb.equal(from.get("company").get("id"), companyId));
		em.createQuery(delete).executeUpdate();
	}
}
