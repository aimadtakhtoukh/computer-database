package com.excilys.console.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.excilys.console.cli.CommandLineInterface;

public class Main {
	
	private final static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
        logger.info("Initializing Spring context.");
        GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext();
        applicationContext.load("applicationContext.xml");
        applicationContext.refresh();
        CommandLineInterface cli = (CommandLineInterface) applicationContext.getBean(CommandLineInterface.class);
		cli.execute();
		logger.info("Closing Spring context.");
		applicationContext.close();
	}

}
