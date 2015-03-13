package main;

import beans.Computer;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;

public class Main {

	public static void main(String[] args) {
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		for (Computer c : dao.getAllComputers()) {
			System.out.println(c.getName() + '\t' + c.getIntroduced() + '\t' + c.getDiscontinued());
		}
	}

}
