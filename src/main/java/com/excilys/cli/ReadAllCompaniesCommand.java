package com.excilys.cli;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.beans.Company;
import com.excilys.cli.page.PageCommandLineInterface;
import com.excilys.page.Page;
import com.excilys.services.CompanyService;
import com.excilys.services.CompanyServiceImpl;

public class ReadAllCompaniesCommand implements Command {
	private CompanyService companyService = CompanyServiceImpl.getInstance();
	
	final Logger logger = LoggerFactory.getLogger(ReadAllCompaniesCommand.class);

	@Override
	public void doAction(List<String> args, Scanner sc) {
		Page<Company> p = companyService.getCompanyPage();
		new PageCommandLineInterface<Company>(p).command(sc);
	}
}