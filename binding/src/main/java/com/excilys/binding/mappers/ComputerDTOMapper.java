package com.excilys.binding.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.excilys.core.beans.Computer;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.validator.DateValidation;
import com.excilys.service.services.CompanyService;

@Component
public class ComputerDTOMapper {
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private DateValidation dateValidator;
	@Autowired
	private MessageSource messageSource;
	
	public ComputerDTO toComputerDTO(Computer bean) {
		if (bean == null) {
			return null;
		}

		String pattern = messageSource.getMessage("validation.date.format", null, LocaleContextHolder.getLocale());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		
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
		String pattern = messageSource.getMessage("validation.date.format", null, LocaleContextHolder.getLocale());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		Computer.Builder builder = Computer.builder().id(dto.getId()).name(dto.getName());
		if (dto.getIntroduced() != null) {
			if (dateValidator.isACorrectDate(dto.getIntroduced())) {
				builder.introduced(LocalDateTime.of(LocalDate.parse(dto.getIntroduced(), formatter), LocalTime.MIDNIGHT));
			}
		}
		if (dto.getDiscontinued() != null) {
			if (dateValidator.isACorrectDate(dto.getDiscontinued())) {
				builder.discontinued(LocalDateTime.of(LocalDate.parse(dto.getDiscontinued(), formatter), LocalTime.MIDNIGHT));
			}
		}
		if (dto.getCompanyId() != null) {
			builder.company(companyService.getCompany(dto.getCompanyId()));
		} 
		return builder.build();
	}
	
}
