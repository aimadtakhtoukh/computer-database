package com.excilys.console.cli;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * CLI command to exit the program, effectively stopping the CLI loop.
 * @author excilys
 *
 */
@Component
public class ExitCommand implements Command {
	
	private final Logger logger = LoggerFactory.getLogger(ExitCommand.class);

	@Override
	public void doAction(List<String> args, Scanner sc) {
		logger.info("Program end.");
		sc.close();
		System.exit(0);
	}

}
