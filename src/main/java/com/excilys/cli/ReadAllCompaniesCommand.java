package com.excilys.cli;

import java.util.List;
import java.util.Scanner;

import com.excilys.beans.Company;
import com.excilys.cli.page.PageCommandLineInterface;
import com.excilys.dao.CompanyDAO;
import com.excilys.dao.CompanyDAOImpl;
import com.excilys.page.Page;

public class ReadAllCompaniesCommand implements Command {
	private CompanyDAO companyDAO = CompanyDAOImpl.getInstance();

	@Override
	public void doAction(List<String> args, Scanner sc) {
		List<Company> companies = companyDAO.getAll();
		if (!companies.isEmpty()) {
			Page<Company> p = new Page<Company>(companies);
			new PageCommandLineInterface<Company>(p).command(sc);
		}
	}
}