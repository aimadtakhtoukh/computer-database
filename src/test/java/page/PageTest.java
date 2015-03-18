package page;

import org.junit.Assert;
import org.junit.Test;

import beans.Computer;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;

public class PageTest {
	
	@Test
	public void aNewStandardPageHasALimitOf10() {
		//GIVEN
		//WHEN
		Page<ComputerDAO, Computer> page = new Page<ComputerDAO, Computer>(ComputerDAOImpl.getInstance());
		Assert.assertEquals(page.getLimit(), 10);
		//THEN
	}
	
	@Test
	public void settingTheLimitOfThePageChangesIt() {
		//GIVEN
		Page<ComputerDAO, Computer> page = new Page<ComputerDAO, Computer>(ComputerDAOImpl.getInstance());
		//WHEN
		page.setLimit(20);
		Assert.assertEquals(page.getLimit(), 20);
	}
	
	@Test
	public void settingTheLimitofThePageToANegativeNumberSetsTheLimitToZero() {
		//GIVEN
		Page<ComputerDAO, Computer> page = new Page<ComputerDAO, Computer>(ComputerDAOImpl.getInstance());
		//WHEN
		page.setLimit(-1);
		Assert.assertEquals(page.getLimit(), 0);
	}
	
	@Test
	public void aNewStandardPageHasAoffsetOf0() {
		//GIVEN
		//WHEN
		Page<ComputerDAO, Computer> page = new Page<ComputerDAO, Computer>(ComputerDAOImpl.getInstance());
		Assert.assertEquals(page.getOffset(), 0);
		//THEN
	}
	
	@Test
	public void settingTheOfftOfThePageChangesIt() {
		//GIVEN
		Page<ComputerDAO, Computer> page = new Page<ComputerDAO, Computer>(ComputerDAOImpl.getInstance());
		//WHEN
		page.setOffset(20);
		Assert.assertEquals(page.getOffset(), 20);
	}
	
	@Test
	public void settingTheOffsetofThePageToANegativeNumberSetTheValueToZero() {
		//GIVEN
		Page<ComputerDAO, Computer> page = new Page<ComputerDAO, Computer>(ComputerDAOImpl.getInstance());
		//WHEN
		page.setLimit(-1);
		Assert.assertEquals(page.getLimit(), 0);
	}	

}
