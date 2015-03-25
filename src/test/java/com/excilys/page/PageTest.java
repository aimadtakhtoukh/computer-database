package com.excilys.page;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;

import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.beans.Computer;
import com.excilys.dao.ComputerDAOImpl;
import com.excilys.dao.ComputerDAOImplTest;
import com.excilys.dao.ComputerDatabaseConnectionFactory;
import com.excilys.dao.util.DatabaseTestUtil;

public class PageTest {

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
	public void aNewStandardPageHasALimitOf10() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		//WHEN
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance().getAll());
		Assert.assertEquals(page.getLimit(), 10);
		//THEN
	}
	
	@Test
	public void settingTheLimitOfThePageChangesIt() throws Exception {
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance().getAll());
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
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance().getAll());
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
		//WHEN
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance().getAll());
		Assert.assertEquals(page.getOffset(), 0);
		//THEN
	}
	
	@Test
	public void settingTheOfftOfThePageChangesIt() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance().getAll());
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
		Page<Computer> page = new Page<Computer>(ComputerDAOImpl.getInstance().getAll());
		//WHEN
		page.setLimit(-1);
		Assert.assertEquals(page.getLimit(), 0);
	}	

}
