package com.excilys.persistence.converter;

import java.sql.Timestamp;

import com.excilys.core.beans.Computer;
import com.excilys.persistence.entity.ComputerEntity;

public class ComputerEntityConverter {
	
	public static Computer to(ComputerEntity entity) {
		if (entity == null) {
			return null;
		}
		Computer.Builder builder = Computer.builder();
		builder.id(entity.getId())
				.name(entity.getName());
		if (entity.getIntroduced() != null) {
			builder.introduced(entity.getIntroduced().toLocalDateTime());
		}
		if (entity.getDiscontinued() != null) {
			builder.discontinued(entity.getDiscontinued().toLocalDateTime());
		}
		if (entity.getCompanyEntity() != null) {
			builder.company(CompanyEntityConverter.to(entity.getCompanyEntity()));
		}
		return builder.build();
	}
	
	public static ComputerEntity from(Computer bean) {
		if (bean == null) {
			return null;
		}
		ComputerEntity.Builder builder = ComputerEntity.builder();
		builder.id(bean.getId())
				.name(bean.getName());
		if (bean.getIntroduced() != null) {
			builder.introduced(Timestamp.valueOf(bean.getIntroduced()));
		}
		if (bean.getDiscontinued() != null) {
			builder.discontinued(Timestamp.valueOf(bean.getDiscontinued()));
		}
		if (bean.getCompany() != null) {
			builder.company(CompanyEntityConverter.from(bean.getCompany()));
		}
		return builder.build();
	}

}
