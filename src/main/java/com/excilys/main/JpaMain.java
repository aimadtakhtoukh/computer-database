package com.excilys.main;

import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.excilys.beans.Computer;
import com.excilys.dao.ComputerDAO;

public class JpaMain {

	public static void main(String[] args) {
		GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext();
        applicationContext.load("applicationContext.xml");
        applicationContext.refresh();
        
        ComputerDAO dao = applicationContext.getBean(ComputerDAO.class);
        List<Computer> computers = dao.getAll();
        for (Computer c : computers) {
        	System.out.println(c) ;
        }
        applicationContext.close();
	}

}
