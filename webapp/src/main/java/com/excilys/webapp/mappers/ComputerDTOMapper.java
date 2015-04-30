package com.excilys.webapp.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.excilys.binding.validator.DateValidation;
import com.excilys.core.beans.Computer;
import com.excilys.service.services.CompanyService;
import com.excilys.webapp.dto.CompanyDTO;
import com.excilys.webapp.dto.ComputerDTO;

@Component
public class ComputerDTOMapper {
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private DateValidation dateValidator;
	@Autowired
	private MessageSource messageSource;
	
	public ComputerDTO to(Computer bean) {
		if (bean == null) {
			return null;
		}
		String pattern = messageSource.getMessage("validation.date.format", null, LocaleContextHolder.getLocale());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		
		ComputerDTO.Builder builder = 
				new ComputerDTO().builder()
				.id(bean.getId())
				.name(bean.getName());

		if (bean.getIntroduced() != null) {
			builder.introduced(bean.getIntroduced().format(formatter));
		}
		if (bean.getDiscontinued() != null) {
			builder.discontinued(bean.getDiscontinued().format(formatter));
		}
		if (bean.getCompany() != null) {
			builder
			.company(CompanyDTO.builder()
					.id(bean.getCompany().getId())
					.name(bean.getCompany().getName())
					.build())			
			.build();
		}
		return builder.build();
	}
	
	public Computer from(ComputerDTO dto) {
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
		if (dto.getCompany() != null) {
			if (dto.getCompany().getId() != null) {
				builder.company(companyService.getCompany(dto.getCompany().getId()));
			}
		} 
		return builder.build();
	}
}
