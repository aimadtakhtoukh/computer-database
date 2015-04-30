package com.excilys.console.cli;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.console.cli.page.PageCommandLineInterface;
import com.excilys.core.beans.Computer;
import com.excilys.persistence.page.Page;
import com.excilys.service.services.ComputerService;
/**
 * CLI command to show a list of computers, paged.
 * @author excilys
 *
 */
@Component
public class ReadAllComputersCommand implements Command {
	
	@Autowired
	private ComputerService service;

	@Override
	public void doAction(List<String> args, Scanner sc) {
		Page<Computer> p = service.getComputerPage();
		new PageCommandLineInterface<Computer>(p).command(sc);		
	}

}
