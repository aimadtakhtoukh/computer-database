package cli;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import page.ComputerPage;
import page.Page;
import page.PageCommandLineInterface;
import beans.Computer;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;

public class ReadAllComputersCommand implements Command {
	
	private ComputerDAO dao = ComputerDAOImpl.getInstance();

	@Override
	public void doAction(List<String> args, Scanner sc) {		
		List<Computer> computers;
		try {
			computers = dao.getAllComputers();
			if (!computers.isEmpty()) {
				Page p = new ComputerPage(computers);
				new PageCommandLineInterface(p).command(sc);
			}
		} catch (SQLException e) {
			System.err.println("An error happened. " + e.getLocalizedMessage());
		}
	}

}
