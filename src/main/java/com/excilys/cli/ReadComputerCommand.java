package com.excilys.cli;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import com.excilys.beans.Computer;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDAOImpl;
import com.excilys.validator.NumberValidator;

public class ReadComputerCommand implements Command {

	private ComputerDAO dao = ComputerDAOImpl.getInstance();
	
	List<Computer> computers = null;
	
	@Override
	public void doAction(List<String> args, Scanner sc) {
		computers = new LinkedList<Computer>();
		Stream<String> arguments = args.stream();
		arguments.forEach(s -> forEachArgument(s));
		if (!computers.isEmpty()) {
			Stream<Computer> computerStream = computers.stream();
			computerStream.forEach(System.out::println);
		}
	}
	
	private void forEachArgument(String s) {
		if (NumberValidator.isARightNumber(s)) {
			Long id = Long.parseLong(s);
			Computer c = dao.get(id);
			if (c != null) {
				computers.add(c);
			} else {
				System.err.println("The computer n° " + id + " doesn't exist.");
			}
		} else {
			System.err.println(s + " isn't a number.");
		}
	}

}
