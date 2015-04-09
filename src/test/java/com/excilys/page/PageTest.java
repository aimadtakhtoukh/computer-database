package com.excilys.page;

import java.io.File;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.dao.ComputerDatabaseConnectionFactory;
import com.excilys.dao.util.DatabaseTestUtil;
import com.excilys.services.ComputerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PageTest {

	@Autowired
	ComputerDatabaseConnectionFactory cdcf;
	@Autowired
	ComputerService service;

	@BeforeClass
	public static void beforeClass() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		DatabaseTestUtil.setUpDatabase();
	}
	
	@Before
	public void prepareTestBase() throws DataSetException, Exception {
		DatabaseTestUtil.executeSqlFile(
				"test.sql", 
				cdcf.getConnection());
	}
	
	@After
	public void cleanup() throws Exception {
        DatabaseTestUtil.databaseTester.onTearDown();
	}
	
	@Test
	public void aNewStandardPageHasALimitOf10() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerPage page = service.getComputerPage();
		//WHEN
		Assert.assertEquals(page.getLimit(), 10);
		//THEN
	}
	
	@Test
	public void settingTheLimitOfThePageChangesIt() throws Exception {
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerPage page = service.getComputerPage();
		//WHEN
		page.setLimit(20);
		Assert.assertEquals(page.getLimit(), 20);
	}
	
	@Test
	public void settingTheLimitofThePageToANegativeNumberSetsTheLimitToZero() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerPage page = service.getComputerPage();
		//WHEN
		page.setLimit(-1);
		Assert.assertEquals(page.getLimit(), 0);
	}
	
	@Test
	public void aNewStandardPageHasAoffsetOf0() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerPage page = service.getComputerPage();
		//WHEN
		Assert.assertEquals(page.getOffset(), 0);
		//THEN
	}
	
	@Test
	public void settingTheOfftOfThePageChangesIt() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerPage page = service.getComputerPage();
		//WHEN
		page.setOffset(20);
		Assert.assertEquals(20, page.getOffset());
	}
	
	@Test
	public void settingTheOffsetofThePageToANegativeNumberSetTheValueToZero() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerPage page = service.getComputerPage();
		//WHEN
		page.setLimit(-1);
		Assert.assertEquals(page.getLimit(), 0);
	}	

}
