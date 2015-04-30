package com.excilys.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.binding.validator.DateValidation;
import com.excilys.binding.validator.StringValidation;
import com.excilys.webapp.dto.ComputerDTO;

@Component
public class ComputerDTOValidator implements Validator {
	
	@Autowired
	private StringValidation stringValidation;
	@Autowired
	private DateValidation dateValidation;
	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ComputerDTO dto = (ComputerDTO) target;
		if (!stringValidation.isACorrectString(dto.getName())) {
			errors.rejectValue("name", "name.required",
					messageSource.getMessage("name.empty.error.message", null, LocaleContextHolder.getLocale()));
		}
		if (dto.getIntroduced() != null) {
			if (!dto.getIntroduced().trim().isEmpty()) {
				if (!dateValidation.isACorrectDate(dto.getIntroduced())) {
					errors.rejectValue("introduced", "introduced.wrong.date", 
							messageSource.getMessage("introduced.bad.format.error.message", null, LocaleContextHolder.getLocale()));
				}
			}
		}
		if (dto.getDiscontinued() != null) {
			if (!dto.getDiscontinued().trim().isEmpty()) {
				if (!dateValidation.isACorrectDate(dto.getDiscontinued())) {
					errors.rejectValue("discontinued", "discontinued.wrong.date", 
							messageSource.getMessage("discontinued.bad.format.error.message", null, LocaleContextHolder.getLocale()));
				}
			}
		}
	}
}
