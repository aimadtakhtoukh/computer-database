package cli;

import java.util.List;
import java.util.Scanner;

import page.Page;
import beans.Company;
import cli.page.PageCommandLineInterface;
import dao.CompanyDAO;
import dao.CompanyDAOImpl;

public class ReadAllCompaniesCommand implements Command {
	private CompanyDAO companyDAO = CompanyDAOImpl.getInstance();

	@Override
	public void doAction(List<String> args, Scanner sc) {
		List<Company> companies = companyDAO.getAll();
		if (!companies.isEmpty()) {
			Page<CompanyDAO, Company> p = new Page<CompanyDAO, Company>(CompanyDAOImpl.getInstance());
			new PageCommandLineInterface<CompanyDAO, Company>(p).command(sc);
		}
	}
}