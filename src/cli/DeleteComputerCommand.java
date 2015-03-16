package cli;

import java.util.List;
import java.util.Scanner;

import dao.ComputerDAO;
import dao.ComputerDAOImpl;

public class DeleteComputerCommand implements Command {
	private ComputerDAO dao = ComputerDAOImpl.getInstance();

	@Override
	public void doAction(List<String> args, Scanner sc) {
		for (String s : args) {
			dao.deleteComputer(Long.parseLong(s));
			System.out.println("Computer " + Long.parseLong(s) + " deleted.");
		}
	}

}
