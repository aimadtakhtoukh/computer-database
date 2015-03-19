package cli;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandLineInterface {
	
	final Logger logger = LoggerFactory.getLogger(CommandLineInterface.class);
	
	public enum ACTIONS {
		CREATE_COMPUTER("create", new CreateComputerCommand()),
		READ_COMPUTER("read", new ReadComputerCommand()),
		READ_ALL_COMPUTERS("read_all", new ReadAllComputersCommand()),
		UPDATE_COMPUTER("update", new UpdateComputerCommand()),
		DELETE_COMPUTER("delete", new DeleteComputerCommand()),
		READ_ALL_COMPANIES("all_companies", new ReadAllCompaniesCommand()),
		EXIT("exit", new ExitCommand());
		
		final Logger logger = LoggerFactory.getLogger(ACTIONS.class);
		
		private String action;
		private Command command;
		
		ACTIONS(String a, Command c) {
			action = a;
			command = c;
		}
		
		public String getAction() {return action;}
		
		public void doAction(List<String> args, Scanner sc) {
			logger.trace("Command " + this.getAction() + " called.");
			command.doAction(args, sc);
			logger.trace("Command " + this.getAction() + " finished.");
		}
		
	}
	
	private Map<String, ACTIONS> actions = new HashMap<>();
	
	public CommandLineInterface() {
		for (ACTIONS a : ACTIONS.values()) {
			actions.put(a.getAction(), a);
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
			if (actions.get(entry.get(0)) != null) {
				actions.get(entry.get(0)).doAction(entry.subList(1, entry.size()), sc);
			}
		}
	}
	

}
