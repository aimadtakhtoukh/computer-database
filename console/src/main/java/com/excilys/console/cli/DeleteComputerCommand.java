package com.excilys.console.cli;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.console.rest.ComputerRestClient;

/**
 * CLI Command deletes a computer(s).
 * @author excilys
 *
 */
@Component
public class DeleteComputerCommand implements Command {
	
	@Autowired
	private ComputerRestClient client;
	
	private final Logger logger = LoggerFactory.getLogger(DeleteComputerCommand.class);

	@Override
	public void doAction(List<String> args, Scanner sc) {
		for (String s : args) {
			try {
				client.deleteComputer(Long.parseLong(s));
				logger.info("Computer " + s + " deleted.");
			} catch (NumberFormatException e) {
				logger.error("The entered number isn't a long.");
			}
		}
	}

}
