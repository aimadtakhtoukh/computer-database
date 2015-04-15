package com.excilys.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NumberValidation {
	
	@Autowired
	private StringValidation stringValidator;

	public boolean isACorrectNumber(String number) {
		if (!stringValidator.isACorrectString(number)) {
			return false;
		}
		Pattern primaryPattern = Pattern.compile("\\d*");
		Matcher primaryMatcher = primaryPattern.matcher(number);
		return primaryMatcher.matches();
	}
}

