package com.excilys.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.beans.Company;
import com.excilys.dao.util.DatabaseTestUtil;

public class CompanyDAOImplTest {
	
	@BeforeClass
	public static void prepareTestBase() throws SQLException, IOException {
		final InputStream is = ComputerDAOImplTest.class
				.getClassLoader().getResourceAsStream("test.sql");
		final BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		final StringBuilder sb = new StringBuilder();
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str + "\n ");
		}
		final Statement stmt = ComputerDatabaseConnectionFactory.getInstance().getConnection().createStatement();
		stmt.execute(sb.toString());
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
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		Company c = dao.get(2);
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
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		Company c = dao.get(-1);
		Assert.assertEquals(c, null);
		//THEN
	}
	
	@Test
	public void getTheCompanyCountReturnsAPositiveInteger() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/companyDAO/get.xml")));
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		Assert.assertTrue(dao.getCount() > 0);
		//THEN
	}
	
	@Test
	public void getAListOfCompanysReturnsANonEmptyList() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/companyDAO/get.xml")));
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		List<Company> list = dao.getAll();
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
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		List<Company> list = dao.getAll(0, 10, null, false, null);
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
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		List<Company> list = dao.getAll(dao.getCount(), 10, null, false, null);
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
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		List<Company> list = dao.getAll(0, dao.getCount(), null, false, null);
		Assert.assertNotEquals(list, null);
		Assert.assertEquals(list.size(), dao.getAll().size());
		//THEN
	}
	
}
