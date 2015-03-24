package com.excilys.cli;

import java.util.List;
import java.util.Scanner;

import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDAOImpl;

public class DeleteComputerCommand implements Command {
	private ComputerDAO dao = ComputerDAOImpl.getInstance();

	@Override
	public void doAction(List<String> args, Scanner sc) {
		for (String s : args) {
			try {
				long id = dao.delete(Long.parseLong(s));
				System.out.println("Computer " + id + " deleted.");
			} catch (NumberFormatException e) {
				System.err.println("The entered number isn't a long.");
			}
		}
	}

}
