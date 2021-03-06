package com.excilys.binding.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.validator.routines.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * The class validates strings. They're tested as true if 
 * dates given are valid in the localized format, and if
 * the date exists and can be handled by the Timestamp SQL type.
 * @author excilys
 *
 */
@Component
public class DateValidation {
	
	@Autowired
	private StringValidation stringValidator;
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Tests if the string is well formed and represents a date.
	 * @param date, a string
	 * @return true if the string represents an existing date.
	 */
	
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
	
	private boolean isDayFromMonthAndYear(int day, int month, int year) {
		// Limits of the Timestamp sql type.
		if (year < 1970 || year > 2037) {
			return false;
		}
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
