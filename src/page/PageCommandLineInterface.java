package page;

import java.util.Scanner;

public class PageCommandLineInterface {
	
	private Page page;
	
	public PageCommandLineInterface(Page p) {
		page = p;
	}
	
	public void command(Scanner sc) {
		page.writeCurrentPage();
		while(true) {
			boolean correctCommand = false;
			String command = sc.nextLine();
			if (command.equals("")) {
				correctCommand = true;
				page.goToNextPage();
				page.writeCurrentPage();
			}
			if (command.equals("next")) {
				correctCommand = true;
				page.goToNextPage();
				page.writeCurrentPage();
			}
			if (command.equals("previous")) {
				correctCommand = true;
				page.goToPreviousPage();
				page.writeCurrentPage();
			}
			if (command.equals("write")) {
				correctCommand = true;
				page.writeCurrentPage();
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

}
