package com.excilys.cli;

import java.util.List;
import java.util.Scanner;

import com.excilys.beans.Computer;
import com.excilys.cli.page.PageCommandLineInterface;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDAOImpl;
import com.excilys.page.Page;

public class ReadAllComputersCommand implements Command {
	
	private ComputerDAO dao = ComputerDAOImpl.getInstance();

	@Override
	public void doAction(List<String> args, Scanner sc) {
		List<Computer> computers = dao.getAll();
		if (!computers.isEmpty()) {
			Page<Computer> p = new Page<Computer>(ComputerDAOImpl.getInstance());
			new PageCommandLineInterface<Computer>(p).command(sc);
		}
	}

}
