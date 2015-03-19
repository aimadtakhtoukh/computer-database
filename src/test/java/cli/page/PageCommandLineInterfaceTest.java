package cli.page;

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

import page.Page;
import beans.Company;
import beans.Computer;
import dao.CompanyDAO;
import dao.ComputerDAO;

@RunWith(MockitoJUnitRunner.class)
public class PageCommandLineInterfaceTest {
	
	ByteArrayOutputStream myOut = new ByteArrayOutputStream();
	
	@Mock
	Page<ComputerDAO, Computer> pageComputer;
	
	@Mock
	Page<CompanyDAO, Company> pageCompany;
	
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
		PageCommandLineInterface<ComputerDAO, Computer> pcli = new PageCommandLineInterface<ComputerDAO, Computer>(pageComputer);
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
		PageCommandLineInterface<CompanyDAO, Company> pcli = new PageCommandLineInterface<CompanyDAO, Company>(pageCompany);
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
		PageCommandLineInterface<ComputerDAO, Computer> pcli = new PageCommandLineInterface<ComputerDAO, Computer>(pageComputer);
		List<Computer> computers = new LinkedList<Computer>();
		Computer c = new Computer();
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
		PageCommandLineInterface<CompanyDAO, Company> pcli 
			= new PageCommandLineInterface<CompanyDAO, Company>(pageCompany);
		List<Company> companies = new LinkedList<Company>();
		Company c = new Company();
		c.setId(1L);
		c.setName("test");
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
