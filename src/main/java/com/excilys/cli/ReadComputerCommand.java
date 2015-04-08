package com.excilys.cli;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.beans.Computer;
import com.excilys.services.ComputerService;
import com.excilys.validator.NumberValidator;

@Component
public class ReadComputerCommand implements Command {
	
	final Logger logger = LoggerFactory.getLogger(ReadComputerCommand.class);
	
	@Autowired
	private ComputerService service;
	
	List<Computer> computers = null;
	
	@Override
	public void doAction(List<String> args, Scanner sc) {
		computers = new LinkedList<Computer>();
		Stream<String> arguments = args.stream();
		arguments.forEach(s -> forEachArgument(s));
		if (!computers.isEmpty()) {
			Stream<Computer> computerStream = computers.stream();
			computerStream.forEach(System.out::println);
		}
	}
	
	private void forEachArgument(String s) {
		if (NumberValidator.isARightNumber(s)) {
			Long id = Long.parseLong(s);
			Computer c = service.getComputer(id);
			if (c != null) {
				computers.add(c);
			} else {
				logger.error("The computer n° " + id + " doesn't exist.");
			}
		} else {
			logger.error(s + " isn't a number.");
		}
	}

}
