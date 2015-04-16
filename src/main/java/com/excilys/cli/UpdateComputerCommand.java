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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import com.excilys.services.CompanyService;
import com.excilys.services.ComputerService;
import com.excilys.validator.DateValidation;
import com.excilys.validator.NumberValidation;
import com.excilys.validator.StringValidation;

public class UpdateComputerCommand implements Command {
	
	private final Logger logger = LoggerFactory.getLogger(UpdateComputerCommand.class);
	
	@Autowired
	private ComputerService service;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private NumberValidation numberValidator;
	@Autowired
	private StringValidation stringValidator;
	@Autowired
	private DateValidation dateValidator;
	@Autowired
	private MessageSource messageSource;
	
	public UpdateComputerCommand() {
		super();
	}

	@Override
	public void doAction(List<String> args, Scanner sc) {
		if (args.size() < 5) {
			System.out.println("Command form : update (id) (computer_name) (introduction_date) (discontinued_date) (company)");
			System.out.println("You can write null instead of a date.");
			return;
		}
		String pattern = messageSource.getMessage("validation.date.format", null, LocaleContextHolder.getLocale());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		Computer c = new Computer();
		if (numberValidator.isACorrectNumber(args.get(0))) {
			c.setId(Long.parseLong(args.get(0)));
		} else {
			logger.error("The id argument must be a number.");
			return;
		}
		if (stringValidator.isACorrectString(args.get(1))) {
			c.setName(args.get(1));
		} else {
			logger.error("The name mustn't be empty.");
			return;
		}
		LocalDateTime introduced;
		if (args.get(2).equals("null")) {
			introduced = null;
		} else {
			if (dateValidator.isACorrectDate(args.get(2))) {
				introduced = LocalDateTime.of(LocalDate.parse(args.get(1), formatter), LocalTime.MIDNIGHT);
			} else {
				introduced = null;
			}
		}
		c.setIntroduced(introduced);
		
		LocalDateTime discontinued;
		if (args.get(3).equals("null")) {
			discontinued = null;
		} else {
			if (dateValidator.isACorrectDate(args.get(3))) {
				discontinued = LocalDateTime.of(LocalDate.parse(args.get(2), formatter), LocalTime.MIDNIGHT);
			} else {
				discontinued = null;
			}
		}
		c.setDiscontinued(discontinued);
		
		if (numberValidator.isACorrectNumber(args.get(4))) {
			long id_company = Long.parseLong(args.get(4));
			Company company = companyService.getCompany(id_company);
			c.setCompany(company);
		} else {
			logger.error("The company id argument must be a number.");
			c.setCompany(null);
		}
		long id = service.updateComputer(c);
		logger.info("Computer " + id + " updated.");
	}

}
