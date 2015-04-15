package com.excilys.cli;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.beans.Computer;
import com.excilys.cli.page.PageCommandLineInterface;
import com.excilys.page.Page;
import com.excilys.services.ComputerService;

public class ReadAllComputersCommand implements Command {
	
	@Autowired
	private ComputerService service;

	@Override
	public void doAction(List<String> args, Scanner sc) {
		Page<Computer> p = service.getComputerPage();
		new PageCommandLineInterface<Computer>(p).command(sc);		
	}

}
