package com.excilys.console.cli.page;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.core.beans.Company;
import com.excilys.core.beans.Computer;
import com.excilys.persistence.page.PageImpl;

@RunWith(MockitoJUnitRunner.class)
public class PageCommandLineInterfaceTest {
	
	ByteArrayOutputStream myOut = new ByteArrayOutputStream();
	
	@Mock
	PageImpl<Computer> pageComputer;
	
	@Mock
	PageImpl<Company> pageCompany;
	
	@Before
	public void beforeTests() {
		System.setOut(new PrintStream(myOut));
	}
	
	@After
	public void afterTests() {
		System.setOut(System.out);
	}
	
	@Test
	public void writeCurrentPageMethodShowsAtLeastOneLineWithComputerBeans() {
		//GIVEN
		PageCommandLineInterface<Computer> pcli = new PageCommandLineInterface<Computer>(pageComputer);
		//WHEN
		pcli.writeCurrentPage(pageComputer);
		String result = myOut.toString();
		Assert.assertNotEquals(null, result);
		Assert.assertNotEquals("", result);
		//THEN
	}
	
	@Test
	public void writeCurrentPageMethodShowsAtLeastOneLineWithCompanyBeans() {
		//GIVEN
		PageCommandLineInterface<Company> pcli = new PageCommandLineInterface<Company>(pageCompany);
		//WHEN
		pcli.writeCurrentPage(pageCompany);
		String result = myOut.toString();
		Assert.assertNotEquals(null, result);
		Assert.assertNotEquals("", result);
		//THEN
	}
	
	@Test
	public void writeCurrentPageMethodShowsAtLeastElevenLinesWithAFullPageOfComputerBeans() {
		//GIVEN
		PageCommandLineInterface<Computer> pcli = new PageCommandLineInterface<Computer>(pageComputer);
		List<Computer> computers = new LinkedList<Computer>();
		Computer c = Computer.builder().id(1L).name("test").build();
		c.setId(1L);
		c.setName("test");
		for (int i = 0; i < 10; i++) {
			computers.add(c);
		}
		Mockito.when(pageComputer.getPageElements()).thenReturn(computers);
		//WHEN
		pcli.writeCurrentPage(pageComputer);
		String result = myOut.toString();
		Assert.assertNotEquals(null, result);
		Assert.assertNotEquals("", result);
		Pattern pattern = Pattern.compile("(.*\\n){11}");
		Matcher matcher = pattern.matcher(result);
		Assert.assertTrue(matcher.matches());
		//THEN
		Mockito.when(pageComputer.getPageElements()).thenReturn(null);
	}
	
	@Test
	public void writeCurrentPageMethodShowsAtLeastElevenLinesWithAFullPageCompanyBeans() {
		//GIVEN
		PageCommandLineInterface<Company> pcli 
			= new PageCommandLineInterface<Company>(pageCompany);
		List<Company> companies = new LinkedList<Company>();
		Company c = Company.builder().id(1L).name("test").build();
		for (int i = 0; i < 10; i++) {
			companies.add(c);
		}
		Mockito.when(pageCompany.getPageElements()).thenReturn(companies);
		//WHEN
		pcli.writeCurrentPage(pageCompany);
		String result = myOut.toString();
		Assert.assertNotEquals(null, result);
		Assert.assertNotEquals("", result);
		Pattern pattern = Pattern.compile("(.*\\n){11}");
		Matcher matcher = pattern.matcher(result);
		Assert.assertTrue(matcher.matches());
		//THEN
		Mockito.when(pageCompany.getPageElements()).thenReturn(null);
	}

}
