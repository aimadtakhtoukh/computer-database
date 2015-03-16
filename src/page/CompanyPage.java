package page;

import java.util.LinkedList;
import java.util.List;

import beans.Company;

public class CompanyPage implements Page {
	
	private List<List<Company>> companies;
	private int currentPage = 0;
	
	public CompanyPage(List<Company> c) {
		companies = new LinkedList<List<Company>>();
		for (int page = 0; page < (c.size() / ELEMENTS_PER_PAGE); page++) {
			List<Company> l = new LinkedList<Company>();
			for (int i = page * ELEMENTS_PER_PAGE; i < page * ELEMENTS_PER_PAGE + ELEMENTS_PER_PAGE; i++) {
				l.add(c.get(i));
			}
			companies.add(l);
		}
		List<Company> l = new LinkedList<Company>();
		for (int i = c.size() - c.size() % ELEMENTS_PER_PAGE; i < c.size(); i++) {
			l.add(c.get(i));
		}
		companies.add(l);
	}

	@Override
	public void writeCurrentPage() {
		for(Company c : companies.get(currentPage)) {
			if (c != null) {
				System.out.println(c.getId() + "\t" + c.getName());
			}
		}
		System.out.println((currentPage + 1) + "/" + (companies.size()));
	}

	@Override
	public void goToNextPage() {
		if (currentPage < companies.size() - 1) {
			currentPage = currentPage + 1;
		}
	}

	@Override
	public void goToPreviousPage() {
		if (currentPage > 0) {
			currentPage = currentPage - 1;
		}
	}

}
