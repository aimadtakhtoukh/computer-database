package cli;

import java.util.List;
import java.util.Scanner;

import page.Page;
import beans.Computer;
import cli.page.PageCommandLineInterface;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;

public class ReadAllComputersCommand implements Command {
	
	private ComputerDAO dao = ComputerDAOImpl.getInstance();

	@Override
	public void doAction(List<String> args, Scanner sc) {
		List<Computer> computers = dao.getAll();
		if (!computers.isEmpty()) {
			Page<ComputerDAO, Computer> p = new Page<ComputerDAO, Computer>(ComputerDAOImpl.getInstance());
			new PageCommandLineInterface<ComputerDAO, Computer>(p).command(sc);
		}
	}

}
