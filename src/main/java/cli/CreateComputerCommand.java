package cli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import beans.Computer;
import dao.CompanyDAOImpl;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;

public class CreateComputerCommand implements Command {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
	private ComputerDAO dao = ComputerDAOImpl.getInstance();
	
	@Override
	public void doAction(List<String> args, Scanner sc) {
		Computer c = new Computer();
		if (args.size() < 4) {
			System.out.println("Command form : create (computer_name) (introduction_date) (discontinued_date) (company)");
			System.out.println("You can write null instead of a date.");
			return;
		}
		c.setName(args.get(0));
		LocalDateTime introduced;
		if (args.get(1).equals("null")) {
			introduced = null;
		} else {
			if (DateVerifier.isTheRightDate(args.get(1))) {
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
			if (DateVerifier.isTheRightDate(args.get(2))) {
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
			c.setCompany(CompanyDAOImpl.getInstance().get(Long.parseLong(args.get(3))));
		} catch (NumberFormatException e) {
			System.err.println("The entered value isn't a long.");
		}
		long id = dao.create(c);
		System.out.println("Computer " + id + " created.");
	}

}
