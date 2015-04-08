package com.excilys.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.cli.CommandLineInterface;

@Component
public class Main {
	
	final static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
        logger.info("Initializing Spring context.");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("./applicationContext.xml");
        CommandLineInterface cli = (CommandLineInterface) applicationContext.getBean(CommandLineInterface.class);
		cli.execute();
		((ConfigurableApplicationContext) applicationContext).close();
		logger.info("Closing Spring context.");
	}

}
