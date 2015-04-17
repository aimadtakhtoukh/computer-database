package com.excilys.core.validator;

import org.springframework.stereotype.Component;

@Component
public class StringValidation {
	
	public boolean isACorrectString(String s) {
		if (s == null) {
			return false;
		}
		return !s.trim().isEmpty();
	}

}
