package com.excilys.cli;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements Command {
	
	final Logger logger = LoggerFactory.getLogger(ExitCommand.class);

	@Override
	public void doAction(List<String> args, Scanner sc) {
		logger.info("Program end.");
		sc.close();
		System.exit(0);
	}

}
