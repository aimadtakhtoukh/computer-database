package page;

import java.util.Scanner;

public class PageCommandLineInterface<T> {
	
	private Page<T> page;
	
	public PageCommandLineInterface(Page<T> p) {
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
	
	public void writeCurrentPage(Page<T> p) {
		System.out.println(p.getOffset());
		for (T t : p.getPageElements()) {
			System.out.println(t);
		}
		System.out.println((p.getCurrentPageNumber() + 1) + "/" + p.getPageTotal());
	}

}
