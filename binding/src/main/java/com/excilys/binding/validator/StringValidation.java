package com.excilys.binding.validator;

import org.springframework.stereotype.Component;

@Component
public class StringValidation {
	
	/**
	 * Return true if the string isn't null or empty.
	 * @param s
	 * @return
	 */
	public boolean isACorrectString(String s) {
		if (s == null) {
			return false;
		}
		return !s.trim().isEmpty();
	}

}
