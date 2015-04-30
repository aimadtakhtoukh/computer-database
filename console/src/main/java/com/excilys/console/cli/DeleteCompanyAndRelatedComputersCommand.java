package com.excilys.console.cli;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.binding.validator.NumberValidation;
import com.excilys.console.rest.CompanyRestClient;

@Component
public class DeleteCompanyAndRelatedComputersCommand implements Command {
	
	private final Logger logger = LoggerFactory.getLogger(DeleteCompanyAndRelatedComputersCommand.class);
	@Autowired
	private CompanyRestClient companyRestClient;
	@Autowired
	private NumberValidation numberValidator;
	
	@Override
	public void doAction(List<String> args, Scanner sc) {
		for (String s : args) {
			if (numberValidator.isACorrectNumber(s)) {
				companyRestClient.deleteCompany(Long.parseLong(s));
				logger.trace("Company " + s + " deleted.");
			} else {
				logger.error("The entered string, " + s + " isn't a long.");
			}
		}
	}

}
