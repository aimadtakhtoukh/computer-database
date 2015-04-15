package com.excilys.cli;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.beans.Computer;
import com.excilys.services.CompanyService;
import com.excilys.services.ComputerService;
import com.excilys.validator.DateValidation;

public class CreateComputerCommand implements Command {
	
	@Autowired
	private ComputerService service;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private DateValidation dateValidator;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	private final Logger logger = LoggerFactory.getLogger(CreateComputerCommand.class);
	
	@Override
	public void doAction(List<String> args, Scanner sc) {
		Computer c = new Computer();
		if (args.size() < 4) {
			logger.error("Command form : create (computer_name) (introduction_date) (discontinued_date) (company)");
			logger.error("You can write null instead of a date.");
			return;
		}
		c.setName(args.get(0));
		LocalDateTime introduced;
		if (args.get(1).equals("null")) {
			introduced = null;
		} else {
			if (dateValidator.isACorrectDate(args.get(1))) {
				introduced = LocalDateTime.of(LocalDate.parse(args.get(1), formatter), LocalTime.MIDNIGHT);
			} else {
				introduced = null;
			}
		}
		c.setIntroduced(introduced);
		LocalDateTime discontinued;
		if (args.get(2).equals("null")) {
			discontinued = null;
		} else {
			if (dateValidator.isACorrectDate(args.get(2))) {
				discontinued = LocalDateTime.of(LocalDate.parse(args.get(2), formatter), LocalTime.MIDNIGHT);
			} else {
				discontinued = null;
			}
		}
		c.setDiscontinued(discontinued);
		try {
			c.setCompany(companyService.getCompany(Long.parseLong(args.get(3))));
		} catch (NumberFormatException e) {
			logger.error("The entered value isn't a long.");
		}
		long id = service.createComputer(c);
		logger.info("Computer " + id + " created.");
	}

}
