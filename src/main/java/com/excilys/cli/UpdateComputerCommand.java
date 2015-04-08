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

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import com.excilys.services.CompanyService;
import com.excilys.services.ComputerService;
import com.excilys.validator.DateValidator;
import com.excilys.validator.NumberValidator;
import com.excilys.validator.StringValidator;

@Component
public class UpdateComputerCommand implements Command {
	
	final Logger logger = LoggerFactory.getLogger(UpdateComputerCommand.class);
	
	private DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
	
	@Autowired
	private ComputerService service;
	@Autowired
	private CompanyService companyService;
	
	public UpdateComputerCommand() {
		super();
		sdf.setLenient(true);
	}

	@Override
	public void doAction(List<String> args, Scanner sc) {
		if (args.size() < 5) {
			System.out.println("Command form : update (id) (computer_name) (introduction_date) (discontinued_date) (company)");
			System.out.println("You can write null instead of a date.");
			return;
		}				
		Computer c = new Computer();
		if (NumberValidator.isARightNumber(args.get(0))) {
			c.setId(Long.parseLong(args.get(0)));
		} else {
			logger.error("The id argument must be a number.");
			return;
		}
		if (StringValidator.isARightString(args.get(1))) {
			c.setName(args.get(1));
		} else {
			logger.error("The name mustn't be empty.");
			return;
		}
		LocalDateTime introduced;
		if (args.get(2).equals("null")) {
			introduced = null;
		} else {
			if (DateValidator.isTheRightDate(args.get(2))) {
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
		if (args.get(3).equals("null")) {
			discontinued = null;
		} else {
			if (DateValidator.isTheRightDate(args.get(3))) {
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
		
		if (NumberValidator.isARightNumber(args.get(4))) {
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
