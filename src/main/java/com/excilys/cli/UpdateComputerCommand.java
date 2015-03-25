package com.excilys.cli;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import com.excilys.dao.CompanyDAOImpl;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.ComputerDAOImpl;
import com.excilys.validator.DateValidator;

public class UpdateComputerCommand implements Command {
	
	private DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
	private ComputerDAO dao = ComputerDAOImpl.getInstance();
	
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
		c.setName(args.get(1));
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
		try {
			long id_company = Long.parseLong(args.get(4));
			Company company = CompanyDAOImpl.getInstance().get(id_company);
			c.setCompany(company);
		} catch (NumberFormatException e) {
			System.err.println("The company id argument must be a number.");
			c.setCompany(null);
		}
		try {
			long id = dao.update(Long.parseLong(args.get(0)), c);
			System.out.println("Computer " + id + " updated.");
		} catch (NumberFormatException e) {
			System.err.println("The id argument must be a number.");
		}
	}

}
