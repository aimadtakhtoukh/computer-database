package com.excilys.cli;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.services.CompanyService;
import com.excilys.validator.NumberValidator;
@Component
public class DeleteCompanyAndRelatedComputersCommand implements Command {
	
	final Logger logger = LoggerFactory.getLogger(DeleteCompanyAndRelatedComputersCommand.class);
	@Autowired
	private CompanyService companyService;

	@Override
	public void doAction(List<String> args, Scanner sc) {
		for (String s : args) {
			if (NumberValidator.isARightNumber(s)) {
				companyService.deleteCompanyAndRelatedComputers(Long.parseLong(s));
				logger.trace("Company " + s + " deleted.");
			} else {
				logger.error("The entered string, " + s + " isn't a long.");
			}
		}
	}

}
