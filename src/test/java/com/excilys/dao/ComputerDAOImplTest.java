package com.excilys.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.excilys.beans.Computer;

public class ComputerDAOImplTest {
	
	@Test
	public void getAComputerWithId2ReturnsAComputer() {
		//GIVEN
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		Computer c = dao.get(2);
		Assert.assertNotEquals(c, null);
		Assert.assertEquals(c.getId(), new Long(2));
		Assert.assertNotEquals(c.getName(), null);
		//THEN
	}
	
	@Test
	public void getAComputerWithNegativeIdReturnsNull() {
		//GIVEN
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		Computer c = dao.get(-1);
		Assert.assertEquals(c, null);
		//THEN
	}
	
	@Test
	public void getTheComputerCountReturnsAPositiveInteger() {
		//GIVEN
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		Assert.assertTrue(dao.getCount() > 0);
		//THEN
	}
	
	@Test
	public void getAListOfComputersReturnsANonEmptyList() {
		//GIVEN
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		List<Computer> list = dao.getAll();
		Assert.assertNotEquals(list, null);
		Assert.assertFalse(list.isEmpty());
		//THEN
	}
	
	@Test
	public void getAListOfComputersWithLimitAndOffsetReturnsAListOfLimitSizeMaximum() {
		//GIVEN
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		List<Computer> list = dao.getAll(0, 10);
		Assert.assertNotEquals(list, null);
		Assert.assertTrue(list.size() <= 10);
		//THEN
	}
	
	@Test
	public void getAListOfComputersWithAnOffsetEqualToItsSizeReturnsAnEmptyList() {
		//GIVEN
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		List<Computer> list = dao.getAll(dao.getCount(), 10);
		Assert.assertNotEquals(list, null);
		Assert.assertTrue(list.isEmpty());
		//THEN
	}
	
	@Test
	public void getAListOfComputersWithAnOffsetEqualToZeroAndALimitEqualToComputerCountReturnsTheSameResultThanGetAll() {
		//GIVEN
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		List<Computer> list = dao.getAll(0, dao.getCount());
		Assert.assertNotEquals(list, null);
		Assert.assertEquals(list.size(), dao.getAll().size());
		//THEN
	}
	
	@Test
	public void insertAComputerAddsAComputerToTheList() {
		//GIVEN
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
	public void insertNullAddsNothingToTheList() {
		//GIVEN
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		//WHEN
		long id = dao.create(null);
		//THEN
		dao.delete(id);
	}
	
	@Test
	public void updateAComputerChangesIt() {
		//GIVEN
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
	public void deleteAComputerRemovesIt() {
		//GIVEN
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
