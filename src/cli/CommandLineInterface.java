package cli;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CommandLineInterface {
	
	public enum ACTIONS {
		CREATE_COMPUTER("create", new CreateComputerCommand()),
		READ_COMPUTER("read", new ReadComputerCommand()),
		READ_ALL_COMPUTERS("read_all", new ReadAllComputersCommand()),
		UPDATE_COMPUTER("update", new UpdateComputerCommand()),
		DELETE_COMPUTER("delete", new DeleteComputerCommand()),
		READ_ALL_COMPANIES("all_companies", new ReadAllCompaniesCommand());
		
		private String action;
		private Command command;
		
		ACTIONS(String a, Command c) {
			action = a;
			command = c;
		}
		
		public String getAction() {return action;}
		
		public void doAction(List<String> args, Scanner sc) {
			command.doAction(args, sc);
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
					a.doAction(entry.subList(1, entry.size()), sc);
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
