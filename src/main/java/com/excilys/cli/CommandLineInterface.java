package com.excilys.cli;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineInterface {
	
	private final Logger logger = LoggerFactory.getLogger(CommandLineInterface.class);
	
	private Map<String, Command> actions = new HashMap<>();
	
	public void execute() {
		actions.put("create", new CreateComputerCommand());
		actions.put("read", new ReadComputerCommand());
		actions.put("read_all", new ReadAllComputersCommand());
		actions.put("update", new UpdateComputerCommand());
		actions.put("delete", new DeleteComputerCommand());
		actions.put("all_companies", new ReadAllCompaniesCommand());
		actions.put("delete_company", new DeleteCompanyAndRelatedComputersCommand());
		actions.put("exit", new ExitCommand());
		
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
				logger.trace(entry.get(0));
				logger.trace("Command " + entry.get(0) + " called.");
				actions.get(entry.get(0)).doAction(entry.subList(1, entry.size()), sc);
				logger.trace("Command " + entry.get(0) + " finished.");
			}
		}
	}
	

}
