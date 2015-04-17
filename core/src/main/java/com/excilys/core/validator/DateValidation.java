package com.excilys.core.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.validator.routines.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DateValidation {
	
	@Autowired
	private StringValidation stringValidator;
	@Autowired
	private MessageSource messageSource;
	
	public boolean isACorrectDate(String date) {
		if (!stringValidator.isACorrectString(date)) {
			return false;
		}
		String pattern = messageSource.getMessage("validation.date.format", null, LocaleContextHolder.getLocale());
		if (!DateValidator.getInstance().isValid(date, pattern)) {
			return false;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate temp = LocalDate.parse(date, formatter);
		return isDayFromMonthAndYear(temp.getDayOfMonth(), temp.getMonthValue(), temp.getYear());
	}
	
	public boolean isDayFromMonthAndYear(int day, int month, int year) {
		if (month < 1 || month > 12) {
			return false;
		}		
		if (day >= 1 && day <= 28) {
			return true;
		}
		if (month != 2) {
			return (day == 29) || (day == 30) || (((day == 31) && !(month == 4 || month == 6 || month == 9 || month == 11)));
		} else {
			if (day == 29) {
				return ((year % 4 == 0) && !(year % 100 == 0)) || ((year % 400) == 0);
			}
		}
		return false;
	}

}
