package com.excilys.cli.page;

import java.util.Scanner;

import com.excilys.page.PageImpl;

public class PageCommandLineInterface<T> {
	
	private PageImpl<T> page;
	
	public PageCommandLineInterface(PageImpl<T> p) {
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
	
	public void writeCurrentPage(PageImpl<T> p) {
		p.getPageElements().stream().forEach(System.out::println);
		System.out.println((p.getCurrentPageNumber() + 1) + "/" + (p.getTotalPageNumber() + 1));
	}

}
