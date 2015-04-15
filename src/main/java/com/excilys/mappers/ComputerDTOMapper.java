package com.excilys.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.beans.Computer;
import com.excilys.controller.dto.ComputerDTO;
import com.excilys.dao.CompanyDAO;
import com.excilys.validator.DateValidation;

@Component
public class ComputerDTOMapper {
	
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private DateValidation dateValidator;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public ComputerDTO toComputerDTO(Computer bean) {
		if (bean == null) {
			return null;
		}
		ComputerDTO dto = new ComputerDTO();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		if (bean.getIntroduced() != null) {
			dto.setIntroduced(bean.getIntroduced().format(formatter));
		}
		if (bean.getDiscontinued() != null) {
			dto.setDiscontinued(bean.getDiscontinued().format(formatter));
		}
		if (bean.getCompany() != null) {
			dto.setCompanyId(bean.getCompany().getId());
			dto.setCompanyName(bean.getCompany().getName());
		} else {
			dto.setCompanyId(null);
			dto.setCompanyName(null);
		}
		return dto;
	}
	
	public Computer toComputer(ComputerDTO dto) {
		if (dto == null) {
			return null;
		}
		Computer bean = new Computer();
		bean.setId(dto.getId());
		bean.setName(dto.getName());
		if (dto.getIntroduced() != null) {
			if (dateValidator.isACorrectDate(dto.getIntroduced())) {
				bean.setIntroduced(LocalDateTime.of(LocalDate.parse(dto.getIntroduced(), formatter), LocalTime.MIDNIGHT));
			} else {
				bean.setIntroduced(null);
			}
		} else {
			bean.setIntroduced(null);
		}
		if (dto.getDiscontinued() != null) {
			if (dateValidator.isACorrectDate(dto.getDiscontinued())) {
				bean.setDiscontinued(LocalDateTime.of(LocalDate.parse(dto.getDiscontinued(), formatter), LocalTime.MIDNIGHT));
			} else {
				bean.setDiscontinued(null);
			}
		} else {
			bean.setDiscontinued(null);
		}
		if (dto.getCompanyId() != null) {
			bean.setCompany(companyDAO.get(dto.getCompanyId()));
		} else {
			bean.setCompany(null);
		}
		return bean;
	}
	
}
