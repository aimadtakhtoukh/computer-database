package com.excilys.page;

import org.junit.Assert;
import org.junit.Test;

import com.excilys.beans.Computer;
import com.excilys.dao.ComputerDAOImpl;

public class PageTest {
	
	@Test
	public void aNewStandardPageHasALimitOf10() {
		//GIVEN
		//WHEN
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance());
		Assert.assertEquals(page.getLimit(), 10);
		//THEN
	}
	
	@Test
	public void settingTheLimitOfThePageChangesIt() {
		//GIVEN
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance());
		//WHEN
		page.setLimit(20);
		Assert.assertEquals(page.getLimit(), 20);
	}
	
	@Test
	public void settingTheLimitofThePageToANegativeNumberSetsTheLimitToZero() {
		//GIVEN
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance());
		//WHEN
		page.setLimit(-1);
		Assert.assertEquals(page.getLimit(), 0);
	}
	
	@Test
	public void aNewStandardPageHasAoffsetOf0() {
		//GIVEN
		//WHEN
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance());
		Assert.assertEquals(page.getOffset(), 0);
		//THEN
	}
	
	@Test
	public void settingTheOfftOfThePageChangesIt() {
		//GIVEN
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance());
		//WHEN
		page.setOffset(20);
		Assert.assertEquals(page.getOffset(), 20);
	}
	
	@Test
	public void settingTheOffsetofThePageToANegativeNumberSetTheValueToZero() {
		//GIVEN
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance());
		//WHEN
		page.setLimit(-1);
		Assert.assertEquals(page.getLimit(), 0);
	}	

}
