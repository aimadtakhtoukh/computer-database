package cli;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import page.CompanyPage;
import page.Page;
import page.PageCommandLineInterface;
import beans.Company;
import dao.CompanyDAO;
import dao.CompanyDAOImpl;

public class ReadAllCompaniesCommand implements Command {
	private CompanyDAO companyDAO = CompanyDAOImpl.getInstance();

	@Override
	public void doAction(List<String> args, Scanner sc) {
		List<Company> companies;
		try {
			companies = companyDAO.getAllCompanies();
			if (!companies.isEmpty()) {
				Page p = new CompanyPage(companies);
				new PageCommandLineInterface(p).command(sc);
			}		
		} catch (SQLException e) {
			System.err.println("An error happened. " + e.getLocalizedMessage());
		}
	}
}