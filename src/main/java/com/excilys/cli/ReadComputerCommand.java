package com.excilys.cli;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.excilys.beans.Computer;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDAOImpl;

public class ReadComputerCommand implements Command {

	private ComputerDAO dao = ComputerDAOImpl.getInstance();
	
	@Override
	public void doAction(List<String> args, Scanner sc) {
		List<Computer> computers = new LinkedList<Computer>();
		for (String s : args) {
			try {
				Computer c = dao.get(Long.parseLong(s));
				if (c != null) {
					computers.add(c);
				} else {
					System.out.println("The computer n° " + Long.parseLong(s) + " doesn't exist.");
				}
			} catch (NumberFormatException e) { 
				System.err.println("Arguments must be numbers.");
			}
		}
		if (!computers.isEmpty()) {
			for (Computer c : computers) {
				System.out.println(c);
			}
		}
	}

}
