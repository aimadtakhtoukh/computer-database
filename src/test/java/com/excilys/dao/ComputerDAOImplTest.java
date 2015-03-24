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

import com.excilys.beans.Computer;
import com.excilys.dao.util.DatabaseTestUtil;

public class ComputerDAOImplTest {

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
	public void getAComputerWithId2ReturnsAComputer() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		Computer c = dao.get(2);
		Assert.assertNotEquals(c, null);
		Assert.assertEquals(c.getId(), new Long(2));
		Assert.assertNotEquals(c.getName(), null);
		//THEN
	}

	@Test
	public void getAComputerWithNegativeIdReturnsNull() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		Computer c = dao.get(-1);
		Assert.assertEquals(c, null);
		//THEN
	}

	@Test
	public void getTheComputerCountReturnsAPositiveInteger() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		Assert.assertTrue(dao.getCount() > 0);
		//THEN
	}

	@Test
	public void getAListOfComputersReturnsANonEmptyList() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		List<Computer> list = dao.getAll();
		Assert.assertNotEquals(list, null);
		Assert.assertFalse(list.isEmpty());
		//THEN
	}

	@Test
	public void getAListOfComputersWithLimitAndOffsetReturnsAListOfLimitSizeMaximum() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		List<Computer> list = dao.getAll(0, 10);
		Assert.assertNotEquals(list, null);
		Assert.assertTrue(list.size() <= 10);
		//THEN
	}

	@Test
	public void getAListOfComputersWithAnOffsetEqualToItsSizeReturnsAnEmptyList() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		List<Computer> list = dao.getAll(dao.getCount(), 10);
		Assert.assertNotEquals(list, null);
		Assert.assertTrue(list.isEmpty());
		//THEN
	}

	@Test
	public void getAListOfComputersWithAnOffsetEqualToZeroAndALimitEqualToComputerCountReturnsTheSameResultThanGetAll() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		List<Computer> list = dao.getAll(0, dao.getCount());
		Assert.assertNotEquals(list, null);
		Assert.assertEquals(list.size(), dao.getAll().size());
		//THEN
	}

	@Test
	public void insertAComputerAddsAComputerToTheList() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		Computer c = new Computer();
		c.setName("test");
		//WHEN
		long id = dao.create(c);
		Assert.assertNotEquals(dao.get(id), null);
		//THEN
		dao.delete(id);
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertNullAddsNothingToTheList() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		long id = dao.create(null);
		//THEN
		dao.delete(id);
	}

	@Test
	public void updateAComputerChangesIt() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		Computer c = new Computer();
		c.setName("test");
		long id = dao.create(c);
		//WHEN
		c.setName("test2");
		dao.update(id, c);
		Assert.assertNotEquals(dao.get(id), null);
		Assert.assertEquals(c.getName(), "test2");
		//THEN
		dao.delete(id);
	}

	@Test
	public void deleteAComputerRemovesIt() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		Computer c = new Computer();
		c.setName("test");
		long id = dao.create(c);
		//WHEN
		dao.delete(id);
		Assert.assertEquals(dao.get(id), null);
		//THEN
	}

}
