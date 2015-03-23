package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberValidator {

	public static boolean isARightNumber(String number) {
		if (!StringValidator.isARightString(number)) {
			return false;
		}
		Pattern primaryPattern = Pattern.compile("\\d*");
		Matcher primaryMatcher = primaryPattern.matcher(number);
		return primaryMatcher.matches();
	}
}

