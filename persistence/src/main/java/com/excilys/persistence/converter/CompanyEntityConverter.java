package com.excilys.persistence.converter;

import com.excilys.core.beans.Company;
import com.excilys.persistence.entity.CompanyEntity;

public class CompanyEntityConverter {
	
	public static Company to(CompanyEntity entity) {
		if (entity == null) {
			return null;
		}
		return Company.builder()
				.id(entity.getId())
				.name(entity.getName())
				.build();
	}
	
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
