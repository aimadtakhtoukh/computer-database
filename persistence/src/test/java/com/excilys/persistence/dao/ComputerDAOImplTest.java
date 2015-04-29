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

import com.excilys.core.beans.Computer;
import com.excilys.persistence.dao.util.ComputerDatabaseConnectionFactory;
import com.excilys.persistence.dao.util.DatabaseTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-persistence.xml")
public class ComputerDAOImplTest {
	
	@Autowired
	ComputerDAO dao;
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
	public void getAComputerWithId2ReturnsAComputer() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/computerDAO/get.xml")));
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
		//WHEN
		List<Computer> list = dao.getAll(0, 10, null, false, null);
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
		//WHEN
		List<Computer> list = dao.getAll(dao.getCount(), 10, null, false, null);
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
		//WHEN
		List<Computer> list = dao.getAll(0, dao.getCount(), null, false, null);
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
		Computer c = Computer.builder().name("test").build();
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
		Computer c = Computer.builder().name("test").build();
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
		Computer c = Computer.builder().name("test").build();
		long id = dao.create(c);
		//WHEN
		dao.delete(id);
		Assert.assertEquals(dao.get(id), null);
		//THEN
	}

}
