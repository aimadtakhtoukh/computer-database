package cli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

import beans.Company;
import beans.Computer;
import dao.CompanyDAO;
import dao.CompanyDAOImpl;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;

public class CommandLineGetter {
	
	public enum ACTIONS {
		CREATE_COMPUTER("create") {
			public void doAction(List<String> args) {
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
					try {
						introduced = LocalDateTime.ofInstant(sdf.parse(args.get(1)).toInstant(), ZoneId.systemDefault());
					} catch (ParseException e) {
						introduced = null;
					}
				}
				c.setIntroduced(introduced);
				LocalDateTime discontinued;
				if (args.get(2).equals("null")) {
					discontinued = null;
				} else {
					try {
						discontinued = LocalDateTime.ofInstant(sdf.parse(args.get(2)).toInstant(), ZoneId.systemDefault());
					} catch (ParseException e) {
						discontinued = null;
					}
				}
				c.setDiscontinued(discontinued);
				c.setCompanyId(Long.parseLong(args.get(3)));
				dao.createComputer(c);
				System.out.println("Computer created.");
			}
		},
		READ_COMPUTER("read") {
			public void doAction(List<String> args) {
				for (String s : args) {
					try {
						Computer c1 = dao.getComputer(Long.parseLong(s));
						System.out.println(c1.getId() + "\t" + c1.getName() + '\t' + c1.getIntroduced() + '\t' 
								+ c1.getDiscontinued() + '\t' + c1.getCompanyId());
					} catch (NullPointerException e) {
						System.out.println("That computer doesn't exist.");
					} catch (NumberFormatException e) { 
						System.out.println("Arguments must be numbers.");
					}
				}
			}
		},
		READ_ALL_COMPUTERS("read_all") {
			public void doAction(List<String> args) {
				for (Computer c1 : dao.getAllComputers()) {
					System.out.println(c1.getId() + "\t" + c1.getName() + '\t' + c1.getIntroduced() + '\t'
							+ c1.getDiscontinued() + '\t' + c1.getCompanyId());
				}				
			}			
		},
		UPDATE_COMPUTER("update") {
			public void doAction(List<String> args) {
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
		},
		DELETE_COMPUTER("delete") {
			public void doAction(List<String> args) {
				for (String s : args) {
					dao.deleteComputer(Long.parseLong(s));
					System.out.println("Computer " + Long.parseLong(s) + " deleted.");
				}
			}
		},
		READ_ALL_COMPANIES("all_companies") {
			public void doAction(List<String> args) {
				for (Company c : companyDAO.getAllCompanies()) {
					System.out.println(c.getId() + "\t" + c.getName());
				}
			}
		};

		ComputerDAO dao = ComputerDAOImpl.getInstance();
		CompanyDAO companyDAO = CompanyDAOImpl.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
		
		private String action;
		ACTIONS(String a) {action = a;}
		
		public String getAction() {return action;}
		
		public void doAction(List<String> args) {
			System.out.println("Not implemented yet.");
		}
		
	}
	
	public void execute() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Enter your command.");
			String read = sc.nextLine();
			StringTokenizer st = new StringTokenizer(read, " ");
			List<String> entry = new LinkedList<String>();
			while (st.hasMoreTokens()) {
				entry.add(st.nextToken());
			}
			for (ACTIONS a : ACTIONS.values()) {
				if (a.getAction().equals(entry.get(0))) {
					a.doAction(entry.subList(1, entry.size()));
				}
			}
			if (entry.get(0).equals("exit")) {
				System.out.println("Program end.");
				break;
			}
		}
		sc.close();
	}
	

}
