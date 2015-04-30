package com.excilys.webservices.dto.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.excilys.core.beans.Computer;
import com.excilys.webservices.dto.ComputerDTO;

public class ComputerDTOConverter {

	public static Computer to(ComputerDTO dto) {
		if (dto == null) {
			return null;
		}
		Computer.Builder builder = Computer.builder().id(dto.getId()).name(dto.getName());
		if (dto.getIntroduced() != null) {
			builder.introduced(LocalDateTime.parse(dto.getIntroduced(), DateTimeFormatter.ISO_DATE_TIME));
		}
		if (dto.getDiscontinued() != null) {
			builder.introduced(LocalDateTime.parse(dto.getDiscontinued(), DateTimeFormatter.ISO_DATE_TIME));
		}
		builder.company(CompanyDTOConverter.to(dto.getCompany()));
		return builder.build();
	}
	
	public static ComputerDTO from(Computer bean) {
		if (bean == null) {
			return null;
		}
		ComputerDTO.Builder builder =  ComputerDTO.builder().id(bean.getId()).name(bean.getName());
		if (bean.getIntroduced() != null) {
			builder.introduced(bean.getIntroduced().format(DateTimeFormatter.ISO_DATE_TIME));
		}
		if (bean.getDiscontinued() != null) {
			builder.introduced(bean.getDiscontinued().format(DateTimeFormatter.ISO_DATE_TIME));
		}
		builder.company(CompanyDTOConverter.from(bean.getCompany()));
		return builder.build();
	}

}
