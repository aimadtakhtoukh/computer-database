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
			try {
				long id = dao.delete(Long.parseLong(s));
				System.out.println("Computer " + id + " deleted.");
			} catch (NumberFormatException e) {
				System.err.println("The entered number isn't a long.");
			}
		}
	}

}
