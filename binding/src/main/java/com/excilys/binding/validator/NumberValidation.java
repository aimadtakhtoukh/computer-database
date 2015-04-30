package com.excilys.binding.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The class validates Strings, if they represents Longs.
 * @author excilys
 *
 */

@Component
public class NumberValidation {
	
	@Autowired
	private StringValidation stringValidator;

	/**
	 * Returns true if the stirng is a number.
	 * @param number
	 * @return
	 */
	public boolean isACorrectNumber(String number) {
		if (!stringValidator.isACorrectString(number)) {
			return false;
		}
		Pattern primaryPattern = Pattern.compile("\\d*");
		Matcher primaryMatcher = primaryPattern.matcher(number);
		return primaryMatcher.matches();
	}
}

