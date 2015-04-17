package com.excilys.console.cli;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.console.cli.page.PageCommandLineInterface;
import com.excilys.core.beans.Company;
import com.excilys.persistence.page.Page;
import com.excilys.service.services.CompanyService;

@Component
public class ReadAllCompaniesCommand implements Command {
	@Autowired
	private CompanyService companyService;
	
	private final Logger logger = LoggerFactory.getLogger(ReadAllCompaniesCommand.class);

	@Override
	public void doAction(List<String> args, Scanner sc) {
		logger.trace("Showing all companies");
		Page<Company> p = companyService.getCompanyPage();
		new PageCommandLineInterface<Company>(p).command(sc);
	}
}