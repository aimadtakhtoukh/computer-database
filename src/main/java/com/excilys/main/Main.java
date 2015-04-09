package com.excilys.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.cli.CommandLineInterface;

@Component
public class Main {
	
	final static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
        logger.info("Initializing Spring context.");
        GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("DEV");
        applicationContext.load("applicationContext.xml");
        applicationContext.refresh();
        CommandLineInterface cli = (CommandLineInterface) applicationContext.getBean(CommandLineInterface.class);
		cli.execute();
		logger.info("Closing Spring context.");
		((ConfigurableApplicationContext) applicationContext).close();
	}

}
