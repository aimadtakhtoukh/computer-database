package com.excilys.validator;

public class StringValidator {
	
	public static boolean isACorrectString(String s) {
		if (s == null) {
			return false;
		}
		return !s.isEmpty();
	}

}
