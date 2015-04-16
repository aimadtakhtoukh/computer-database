package com.excilys.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.controller.dto.ComputerDTO;

@Component
public class ComputerDTOValidator implements Validator {
	
	@Autowired
	private StringValidation stringValidation;
	@Autowired
	private DateValidation dateValidation;

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ComputerDTO dto = (ComputerDTO) target;
		if (!stringValidation.isACorrectString(dto.getName())) {
			errors.reject("name", "name.required");
		}
		if (dto.getIntroduced() != null) {
			if (!dto.getIntroduced().trim().isEmpty()) {
				if (!dateValidation.isACorrectDate(dto.getIntroduced())) {
					errors.reject("introduced", "introduced.wrong.date");
				}
			}
		}
		if (dto.getDiscontinued() != null) {
			if (!dto.getDiscontinued().trim().isEmpty()) {
				if (!dateValidation.isACorrectDate(dto.getDiscontinued())) {
					errors.reject("discontinued", "discontinued.wrong.date");
				}
			}
		}
	}
}
