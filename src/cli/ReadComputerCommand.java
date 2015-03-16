package cli;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import dao.ComputerDAO;
import dao.ComputerDAOImpl;
import page.ComputerPage;
import page.Page;
import page.PageCommandLineInterface;
import beans.Computer;

public class ReadComputerCommand implements Command {

	private ComputerDAO dao = ComputerDAOImpl.getInstance();
	
	@Override
	public void doAction(List<String> args, Scanner sc) {
		List<Computer> computers = new LinkedList<Computer>();
		for (String s : args) {
			try {
				Computer c = dao.getComputer(Long.parseLong(s));
				if (c != null) {
					computers.add(c);
				} else {
					System.out.println("The computer n° " + Long.parseLong(s) + " doesn't exist.");
				}
			} catch (NumberFormatException e) { 
				System.out.println("Arguments must be numbers.");
			}
		}
		if (!computers.isEmpty()) {
			Page p = new ComputerPage(computers);
			new PageCommandLineInterface(p).command(sc);
		}
	}

}
