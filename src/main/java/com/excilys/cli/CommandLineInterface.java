package com.excilys.cli;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLineInterface {
	
	final Logger logger = LoggerFactory.getLogger(CommandLineInterface.class);
	/*
	public enum ACTIONS {
		CREATE_COMPUTER("create", new CreateComputerCommand()),
		READ_COMPUTER("read", new ReadComputerCommand()),
		READ_ALL_COMPUTERS("read_all", new ReadAllComputersCommand()),
		UPDATE_COMPUTER("update", new UpdateComputerCommand()),
		DELETE_COMPUTER("delete", new DeleteComputerCommand()),
		READ_ALL_COMPANIES("all_companies", new ReadAllCompaniesCommand()),
		DELETE_COMPANY_AND_RELATED_COMPUTERS("delete_company", new DeleteCompanyAndRelatedComputersCommand()),
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
	*/

	@Autowired
	CreateComputerCommand createComputerCommand;
	@Autowired
	ReadComputerCommand readComputerCommand;
	@Autowired
	ReadAllComputersCommand readAllComputersCommand;
	@Autowired
	UpdateComputerCommand updateComputerCommand;
	@Autowired
	DeleteComputerCommand deleteComputerCommand;
	@Autowired
	ReadAllCompaniesCommand readAllCompaniesCommand;
	@Autowired
	DeleteCompanyAndRelatedComputersCommand deleteCompanyAndRelatedComputersCommand;
	@Autowired
	ExitCommand exitCommand;
	
	private Map<String, Command> actions = new HashMap<>();
	
	public void execute() {
		actions.put("create", createComputerCommand);
		actions.put("read", readComputerCommand);
		actions.put("read_all", readAllComputersCommand);
		actions.put("update", updateComputerCommand);
		actions.put("delete", deleteComputerCommand);
		actions.put("all_companies", readAllCompaniesCommand);
		actions.put("delete_company", deleteCompanyAndRelatedComputersCommand);
		actions.put("exit", exitCommand);
		
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
