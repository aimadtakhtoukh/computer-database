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
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.excilys.binding.validator.DateValidation;
import com.excilys.binding.validator.NumberValidation;
import com.excilys.binding.validator.StringValidation;
import com.excilys.console.rest.CompanyRestClient;
import com.excilys.console.rest.ComputerRestClient;
import com.excilys.core.beans.Company;
import com.excilys.core.beans.Computer;

/**
 * CLI command to update a computer.
 * @author excilys
 *
 */
@Component
public class UpdateComputerCommand implements Command {
	
	private final Logger logger = LoggerFactory.getLogger(UpdateComputerCommand.class);
	
	@Autowired
	private ComputerRestClient client;
	@Autowired
	private CompanyRestClient companyClient;
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
		Computer.Builder builder = Computer.builder();
		if (numberValidator.isACorrectNumber(args.get(0))) {
			builder.id(Long.parseLong(args.get(0)));
		} else {
			logger.error("The id argument must be a number.");
			return;
		}
		if (stringValidator.isACorrectString(args.get(1))) {
			builder.name(args.get(1));
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
		builder.introduced(introduced);
		
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
		builder.discontinued(discontinued);
		
		if (numberValidator.isACorrectNumber(args.get(4))) {
			long id_company = Long.parseLong(args.get(4));
			Company company = companyClient.getCompany(id_company);
			builder.company(company);
		} else {
			logger.error("The company id argument must be a number.");
			builder.company(null);
		}
		long id = client.editComputer(builder.build());
		logger.info("Computer " + id + " updated.");
	}

}
