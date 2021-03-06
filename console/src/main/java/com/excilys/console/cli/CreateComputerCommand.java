package com.excilys.console.cli;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.excilys.console.rest.CompanyRestClient;
import com.excilys.console.rest.ComputerRestClient;
import com.excilys.core.beans.Computer;

import com.excilys.binding.validator.DateValidation;

/**
 * CLI command to create a computer in the database.
 * @author excilys
 *
 */
@Component
public class CreateComputerCommand implements Command {
	
	@Autowired
	private ComputerRestClient client;
	@Autowired
	private CompanyRestClient companyClient;
	@Autowired
	private DateValidation dateValidator;
	@Autowired
	private MessageSource messageSource;
	
	private final Logger logger = LoggerFactory.getLogger(CreateComputerCommand.class);
	
	@Override
	public void doAction(List<String> args, Scanner sc) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if (args.size() < 4) {
			logger.error("Command form : create (computer_name) (introduction_date) (discontinued_date) (company)");
			logger.error("You can write null instead of a date.");
			return;
		}
		Computer.Builder builder = Computer.builder().name(args.get(0));
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
		builder.introduced(introduced);
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
		builder.discontinued(discontinued);
		try {
			builder.company(companyClient.getCompany(Long.parseLong(args.get(3))));
		} catch (NumberFormatException e) {
			logger.error("The entered value isn't a long.");
		}
		long id = client.addComputer(builder.build());
		logger.info("Computer " + id + " created.");
	}

}
