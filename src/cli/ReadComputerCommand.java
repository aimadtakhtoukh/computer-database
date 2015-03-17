package cli;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import page.Page;
import page.PageCommandLineInterface;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;
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
				System.err.println("Arguments must be numbers.");
			} catch (SQLException e) {
				//System.err.println("An error happened. " + e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		if (!computers.isEmpty()) {
			Page<Computer> p = new Page<Computer>(computers);
			new PageCommandLineInterface<Computer>(p).command(sc);
		}
	}

}
