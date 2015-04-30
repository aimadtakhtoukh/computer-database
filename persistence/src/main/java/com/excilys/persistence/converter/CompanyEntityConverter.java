package com.excilys.persistence.converter;

import com.excilys.core.beans.Company;
import com.excilys.persistence.entity.CompanyEntity;

/**
 * Converts a company entity into a crossplatform bean, and the opposite..
 * @author excilys
 *
 */
public class CompanyEntityConverter {
	/**
	 * Turns the company entity into an equivalent company bean.
	 * @param entity
	 * @return
	 */
	public static Company to(CompanyEntity entity) {
		if (entity == null) {
			return null;
		}
		return Company.builder()
				.id(entity.getId())
				.name(entity.getName())
				.build();
	}

	/**
	 * Turns the company bean into an equivalent company entity.
	 * @param entity
	 * @return
	 */
	public static CompanyEntity from(Company bean) {
		if (bean == null) {
			return null;
		}
		return CompanyEntity.builder()
				.id(bean.getId())
				.name(bean.getName())
				.build();
	}

}
