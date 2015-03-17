package cli;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import beans.Company;
import beans.Computer;
import dao.CompanyDAOImpl;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;

public class UpdateComputerCommand implements Command {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
	private ComputerDAO dao = ComputerDAOImpl.getInstance();

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
			try {
				introduced = LocalDateTime.ofInstant(sdf.parse(args.get(1)).toInstant(), ZoneId.systemDefault());
			} catch (ParseException e) {
				introduced = null;
			}
		}
		c.setIntroduced(introduced);
		LocalDateTime discontinued;
		if (args.get(3).equals("null")) {
			discontinued = null;
		} else {
			try {
				discontinued = LocalDateTime.ofInstant(sdf.parse(args.get(2)).toInstant(), ZoneId.systemDefault());
			} catch (ParseException e) {
				discontinued = null;
			}
		}
		c.setDiscontinued(discontinued);
		try {
			long id_company = Long.parseLong(args.get(4));
			Company company = CompanyDAOImpl.getInstance().getCompany(id_company);
			c.setCompany(company);
		} catch (NumberFormatException e) {
			System.err.println("The company id argument must be a number.");
			c.setCompany(null);
		} catch (SQLException e) {
			System.err.println("An error happened. " + e.getLocalizedMessage());
			c.setCompany(null);
		}
		try {
			long id = dao.updateComputer(Long.parseLong(args.get(0)), c);
			System.out.println("Computer " + id + " updated.");
		} catch (NumberFormatException e) {
			System.err.println("The id argument must be a number.");
		} catch (SQLException e) {
			System.err.println("An error happened.");
			e.printStackTrace();
		}
	}

}
