package page;

import java.util.LinkedList;
import java.util.List;

import beans.Computer;

public class ComputerPage implements Page {
	
	private List<List<Computer>> computers;
	private int currentPage = 0;
	
	public ComputerPage(List<Computer> c) {
		computers = new LinkedList<List<Computer>>();
		for (int page = 0; page < (c.size() / ELEMENTS_PER_PAGE); page++) {
			List<Computer> l = new LinkedList<Computer>();
			for (int i = page * ELEMENTS_PER_PAGE; i < page * ELEMENTS_PER_PAGE + ELEMENTS_PER_PAGE; i++) {
				l.add(c.get(i));
			}
			computers.add(l);
		}
		List<Computer> l = new LinkedList<Computer>();
		for (int i = c.size() - c.size() % ELEMENTS_PER_PAGE; i < c.size(); i++) {
			l.add(c.get(i));
		}
		computers.add(l);
	}

	@Override
	public void writeCurrentPage() {
		for(Computer c : computers.get(currentPage)) {
			if (c != null) {
				System.out.println(c.getId() + "\t" + c.getName() + '\t' + c.getIntroduced() + '\t' 
						+ c.getDiscontinued() + '\t' + c.getCompanyId());
			}
		}
		System.out.println((currentPage + 1) + "/" + computers.size());
	}

	@Override
	public void goToNextPage() {
		if (currentPage < computers.size() - 1) {
			currentPage++;
		}
	}

	@Override
	public void goToPreviousPage() {
		if (currentPage > 0) {
			currentPage--;
		}
	}

}
