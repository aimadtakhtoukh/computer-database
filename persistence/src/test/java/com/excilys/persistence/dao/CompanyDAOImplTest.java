package com.excilys.persistence.dao;

import java.io.File;
import java.util.List;

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

import com.excilys.core.beans.Company;
import com.excilys.persistence.dao.util.ComputerDatabaseConnectionFactory;
import com.excilys.persistence.dao.util.DatabaseTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-persistence.xml")
public class CompanyDAOImplTest {
	
	@Autowired
	CompanyDAO companyDAO;
	@Autowired
	ComputerDatabaseConnectionFactory cdcf;
	
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
	public void getACompanyWithId2ReturnsACompany() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/companyDAO/get.xml")));
		//WHEN
		Company c = companyDAO.get(2);
		Assert.assertNotEquals(c, null);
		Assert.assertEquals(c.getId(), new Long(2));
		Assert.assertNotEquals(c.getName(), null);
		//THEN
	}
	
	@Test
	public void getACompanyWithNegativeIdReturnsNull() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/companyDAO/get.xml")));
		//WHEN
		Company c = companyDAO.get(-1);
		Assert.assertEquals(c, null);
		//THEN
	}
	
	@Test
	public void getTheCompanyCountReturnsAPositiveInteger() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/companyDAO/get.xml")));
		//WHEN
		Assert.assertTrue(companyDAO.getCount() > 0);
		//THEN
	}
	
	@Test
	public void getAListOfCompanysReturnsANonEmptyList() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/companyDAO/get.xml")));
		//WHEN
		List<Company> list = companyDAO.getAll();
		Assert.assertNotEquals(list, null);
		Assert.assertFalse(list.isEmpty());
		//THEN
	}
	
	@Test
	public void getAListOfCompaniesWithLimitAndOffsetReturnsAListOfLimitSizeMaximum() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/companyDAO/get.xml")));
		//WHEN
		List<Company> list = companyDAO.getAll(0, 10, null, false, null);
		Assert.assertNotEquals(list, null);
		Assert.assertTrue(list.size() <= 10);
		//THEN
	}
	
	@Test
	public void getAListOfCompaniesWithAnOffsetEqualToItsSizeReturnsAnEmptyList() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/companyDAO/get.xml")));
		//WHEN
		List<Company> list = companyDAO.getAll(companyDAO.getCount(), 10, null, false, null);
		Assert.assertNotEquals(list, null);
		Assert.assertTrue(list.isEmpty());
		//THEN
	}
	
	@Test
	public void getAListOfCompaniesWithAnOffsetEqualToZeroAndALimitEqualToCompanyCountReturnsTheSameResultThanGetAll() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/companyDAO/get.xml")));
		//WHEN
		List<Company> list = companyDAO.getAll(0, companyDAO.getCount(), null, false, null);
		Assert.assertNotEquals(list, null);
		Assert.assertEquals(list.size(), companyDAO.getAll().size());
		//THEN
	}
}
