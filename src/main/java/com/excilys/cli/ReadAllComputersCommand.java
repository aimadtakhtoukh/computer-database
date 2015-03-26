package com.excilys.cli;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.beans.Computer;
import com.excilys.cli.page.PageCommandLineInterface;
import com.excilys.page.PageImpl;
import com.excilys.services.ComputerService;
import com.excilys.services.ComputerServiceImpl;

public class ReadAllComputersCommand implements Command {
	
	final Logger logger = LoggerFactory.getLogger(DeleteCompanyAndRelatedComputersCommand.class);
	
	private ComputerService service = ComputerServiceImpl.getInstance();

	@Override
	public void doAction(List<String> args, Scanner sc) {
		List<Computer> computers = service.getAllComputers();
		if (!computers.isEmpty()) {
			PageImpl<Computer> p = new PageImpl<Computer>(computers);
			new PageCommandLineInterface<Computer>(p).command(sc);
		}
	}

}
