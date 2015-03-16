package main;

import cli.CommandLineInterface;


public class Main {

	public static void main(String[] args) {
		/*
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		for (Computer c1 : dao.getAllComputers()) {
			System.out.println(c1.getName() + '\t' + c1.getIntroduced() + '\t' + c1.getDiscontinued());
		}
		System.out.println("=======================");
		Computer c2 = new Computer();
		c2.setName("Nintendo DS lol");
		//c2.setIntroduced(LocalDateTime.MIN);
		c2.setCompanyId(2);
		dao.updateComputer(575, c2);
		dao.updateComputer(576, c2);
		for (Computer c1 : dao.getAllComputers()) {
			System.out.println(c1.getId() + "\t" + c1.getName() + '\t' + c1.getIntroduced() + '\t' + c1.getDiscontinued());
		}
		dao.deleteComputer(577);
		Computer c1 = dao.getComputer(577L);
		System.out.println(c1.getId() + "\t" + c1.getName() + '\t' + c1.getIntroduced() + '\t' + c1.getDiscontinued());
		
		*/
		new CommandLineInterface().execute();
	}

}
