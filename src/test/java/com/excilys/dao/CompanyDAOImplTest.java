package com.excilys.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.excilys.beans.Company;

public class CompanyDAOImplTest {

	@Test
	public void getACompanyWithId2ReturnsACompany() {
		//GIVEN
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		Company c = dao.get(2);
		Assert.assertNotEquals(c, null);
		Assert.assertEquals(c.getId(), new Long(2));
		Assert.assertNotEquals(c.getName(), null);
		//THEN
	}
	
	@Test
	public void getACompanyWithNegativeIdReturnsNull() {
		//GIVENmatcher.matches()
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		Company c = dao.get(-1);
		Assert.assertEquals(c, null);
		//THEN
	}
	
	@Test
	public void getTheCompanyCountReturnsAPositiveInteger() {
		//GIVEN
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		Assert.assertTrue(dao.getCount() > 0);
		//THEN
	}
	
	@Test
	public void getAListOfCompanysReturnsANonEmptyList() {
		//GIVEN
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		List<Company> list = dao.getAll();
		Assert.assertNotEquals(list, null);
		Assert.assertFalse(list.isEmpty());
		//THEN
	}
	
	@Test
	public void getAListOfCompaniesWithLimitAndOffsetReturnsAListOfLimitSizeMaximum() {
		//GIVEN
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		List<Company> list = dao.getAll(0, 10);
		Assert.assertNotEquals(list, null);
		Assert.assertTrue(list.size() <= 10);
		//THEN
	}
	
	@Test
	public void getAListOfCompaniesWithAnOffsetEqualToItsSizeReturnsAnEmptyList() {
		//GIVEN
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		List<Company> list = dao.getAll(dao.getCount(), 10);
		Assert.assertNotEquals(list, null);
		Assert.assertTrue(list.isEmpty());
		//THEN
	}
	
	@Test
	public void getAListOfCompaniesWithAnOffsetEqualToZeroAndALimitEqualToCompanyCountReturnsTheSameResultThanGetAll() {
		//GIVEN
		CompanyDAO dao = CompanyDAOImpl.getInstance();
		//WHEN
		List<Company> list = dao.getAll(0, dao.getCount());
		Assert.assertNotEquals(list, null);
		Assert.assertEquals(list.size(), dao.getAll().size());
		//THEN
	}
	
}
