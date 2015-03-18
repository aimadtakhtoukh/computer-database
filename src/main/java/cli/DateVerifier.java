package cli;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateVerifier {
	
	public static boolean isTheRightDate(String date) {
		Pattern primaryPattern = Pattern.compile("(\\d{2})-(\\d{2})-(\\d{4})");
		Matcher primaryMatcher = primaryPattern.matcher(date);
		if (!primaryMatcher.matches()) {
			return false;
		}
		int day = Integer.parseInt(primaryMatcher.group(1));
		int month = Integer.parseInt(primaryMatcher.group(2));
		int year = Integer.parseInt(primaryMatcher.group(3));
		return isDayFromMonthAndYear(day, month, year);
	}
	
	public static boolean isDayFromMonthAndYear(int day, int month, int year) {
		if (month < 1 || month > 12) {
			return false;
		}		
		if (day >= 1 && day <= 28) {
			return true;
		}
		if (month != 2) {
			return (day == 30) || (((day == 31) && !(month == 4 || month == 6 || month == 9 || month == 11)));
		} else {
			if (day == 29) {
				return ((year % 4 == 0) && !(year % 100 == 0)) || ((year % 400) == 0);
			}
		}
		return false;
	}

}
