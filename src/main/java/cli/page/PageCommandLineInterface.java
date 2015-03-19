package cli.page;

import java.util.Scanner;

import page.Page;
import dao.CRUDDAO;

public class PageCommandLineInterface<D extends CRUDDAO<T>, T> {
	
	private Page<D, T> page;
	
	public PageCommandLineInterface(Page<D, T> p) {
		page = p;
	}
	
	public void command(Scanner sc) {
		writeCurrentPage(page);
		while(true) {
			boolean correctCommand = false;
			String command = sc.nextLine();
			if (command.equals("")) {
				correctCommand = true;
				page.goToPage(page.getCurrentPageNumber() + 1);
				writeCurrentPage(page);
			}
			if (command.equals("next")) {
				correctCommand = true;
				page.goToPage(page.getCurrentPageNumber() + 1);
				writeCurrentPage(page);
			}
			if (command.equals("previous")) {
				correctCommand = true;
				page.goToPage(page.getCurrentPageNumber() - 1);
				writeCurrentPage(page);
			}
			if (command.equals("write")) {
				correctCommand = true;
				writeCurrentPage(page);
			}
			if (command.equals("stop")) {
				correctCommand = true;
				break;
			}
			if (!correctCommand) {
				System.out.println("Type stop to get out of the pagination mode.");
			}
		}	
	}
	
	public void writeCurrentPage(Page<D, T> p) {
		for (T t : p.getPageElements()) {
			System.out.println(t);
		}
		System.out.println((p.getCurrentPageNumber() + 1) + "/" + (p.getTotalPageNumber() + 1));
	}

}
