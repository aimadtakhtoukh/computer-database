package cli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import dao.ComputerDAO;
import dao.ComputerDAOImpl;
import beans.Computer;

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
		c.setCompanyId(Long.parseLong(args.get(4)));
		dao.updateComputer(Long.parseLong(args.get(0)), c);
		System.out.println("Computer " + Long.parseLong(args.get(0)) + " updated.");
	}

}
