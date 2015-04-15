package com.excilys.cli;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.beans.Computer;
import com.excilys.services.CompanyService;
import com.excilys.services.ComputerService;
import com.excilys.validator.DateValidator;

@Component
public class CreateComputerCommand implements Command {
	
	private DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
	@Autowired
	private ComputerService service;
	@Autowired
	private CompanyService companyService;
	
	final Logger logger = LoggerFactory.getLogger(CreateComputerCommand.class);
	
	public CreateComputerCommand() {
		sdf.setLenient(true);
	}
	
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
			if (DateValidator.isACorrectDate(args.get(1))) {
				try {
					introduced = LocalDateTime.ofInstant(sdf.parse(args.get(1)).toInstant(), ZoneId.systemDefault());
				} catch (ParseException e) {
					introduced = null;
				}
			} else {
				introduced = null;
			}
		}
		c.setIntroduced(introduced);
		LocalDateTime discontinued;
		if (args.get(2).equals("null")) {
			discontinued = null;
		} else {
			if (DateValidator.isACorrectDate(args.get(2))) {
				try {
					discontinued = LocalDateTime.ofInstant(sdf.parse(args.get(2)).toInstant(), ZoneId.systemDefault());
				} catch (ParseException e) {
					discontinued = null;
				}
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
