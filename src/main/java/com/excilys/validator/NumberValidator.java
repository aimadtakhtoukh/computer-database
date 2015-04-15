package com.excilys.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberValidator {

	public static boolean isACorrectNumber(String number) {
		if (!StringValidator.isACorrectString(number)) {
			return false;
		}
		Pattern primaryPattern = Pattern.compile("\\d*");
		Matcher primaryMatcher = primaryPattern.matcher(number);
		return primaryMatcher.matches();
	}
}

