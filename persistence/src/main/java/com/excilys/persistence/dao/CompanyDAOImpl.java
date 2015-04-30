package com.excilys.persistence.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.core.beans.Company;
import com.excilys.persistence.converter.CompanyEntityConverter;
import com.excilys.persistence.entity.CompanyEntity;

@Repository
public class CompanyDAOImpl implements CompanyDAO {
	
	private final Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	@PersistenceContext(unitName = "ComputerDatabasePU")
	private EntityManager em;

	@Override
	public Company get(long id) {
		logger.trace("CompanyDAO doit récupérer le Company d'id " + id);
		return CompanyEntityConverter.to(em.find(CompanyEntity.class, id));
	}
	
	@Override
	public List<Company> getAll(int offset, int limit, String orderBy, boolean ascendant, String searchString) {
		logger.trace("CompanyDAO récupère tous les Companies de " + offset + " à " + (offset + limit) + ".");
		logger.trace("OrderBy : " + orderBy);
		logger.trace("Ascendant : " + ascendant);
		logger.trace("Search String : " + searchString);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CompanyEntity> cq = cb.createQuery(CompanyEntity.class);
		Root<CompanyEntity> from = cq.from(CompanyEntity.class);
		Predicate searchPredicate = cb.and();
		if (searchString != null) {
			if (!searchString.trim().isEmpty()) {
				String searchLikeString = "%" + searchString + "%";
				searchPredicate = cb.like(from.get("name"), searchLikeString);
			}
		}
		cq.where(searchPredicate);
		cq.select(from);
		if (orderBy != null) {
			if (!orderBy.trim().isEmpty()) {
				Order o;
				if (ascendant) {
					o = cb.asc(from.get(orderBy));
				} else {
					o = cb.desc(from.get(orderBy));
				}
				cq.orderBy(o);
			}
		}
		TypedQuery<CompanyEntity> q = em.createQuery(cq);
		q.setFirstResult(offset);
		q.setMaxResults(limit);
		return q.getResultList().stream().map(CompanyEntityConverter::to).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public long delete(long id) {
		logger.trace("CompanyDAO supprime la Company d'id " + id);
		em.remove(em.find(CompanyEntity.class, id));
		return id;
	}

	@Override
	public List<Company> getAll() {
		logger.trace("Company DAO récupère toutes les Companies.");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CompanyEntity> cq = cb.createQuery(CompanyEntity.class);
		Root<CompanyEntity> from = cq.from(CompanyEntity.class);
		cq.select(from);
		TypedQuery<CompanyEntity> q = em.createQuery(cq);
		return q.getResultList().stream().map(CompanyEntityConverter::to).collect(Collectors.toList());
	}

	@Override
	public int getCount() {
		logger.trace("CompanyDAO renvoie le nombre de Companies.");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(CompanyEntity.class)));
		return em.createQuery(cq).getSingleResult().intValue();
	}
}
