package com.excilys.webservices.dto.converter;

import com.excilys.core.beans.Company;
import com.excilys.webservices.dto.CompanyDTO;

public class CompanyDTOConverter {

	public static Company to(CompanyDTO dto) {
		if (dto == null) {
			return null;
		}
		return Company.builder().id(dto.getId()).name(dto.getName()).build();
	}
	
	public static CompanyDTO from(Company bean) {
		if (bean == null) {
			return null;
		}
		return CompanyDTO.builder().id(bean.getId()).name(bean.getName()).build();
	}
	
}
