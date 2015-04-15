package com.excilys.cli;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.validator.DateValidation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DateVerifierTest {
	
	@Autowired
	private DateValidation dateValidator;
	
	@Test
	public void test4Verifier() {
		Assert.assertTrue(dateValidator.isACorrectDate("31-01-1992"));
		Assert.assertFalse(dateValidator.isACorrectDate("31-02-1992"));
		Assert.assertTrue(dateValidator.isACorrectDate("31-03-1992"));
		Assert.assertFalse(dateValidator.isACorrectDate("31-04-1992"));
		Assert.assertTrue(dateValidator.isACorrectDate("31-05-1992"));
		Assert.assertFalse(dateValidator.isACorrectDate("31-06-1992"));
		Assert.assertTrue(dateValidator.isACorrectDate("31-07-1992"));
		Assert.assertTrue(dateValidator.isACorrectDate("31-08-1992"));
		Assert.assertFalse(dateValidator.isACorrectDate("31-09-1992"));
		Assert.assertTrue(dateValidator.isACorrectDate("31-10-1992"));
		Assert.assertFalse(dateValidator.isACorrectDate("31-11-1992"));
		Assert.assertTrue(dateValidator.isACorrectDate("31-12-1992"));
	}
	
	@Test
	public void test1Verifier() {
		Assert.assertTrue(dateValidator.isACorrectDate("29-02-1992"));
	}
	
	@Test
	public void test2Verifier() {
		Assert.assertFalse(dateValidator.isACorrectDate("29-02-1993"));
	}
	
	@Test
	public void test3Verifier() {
		Assert.assertTrue(dateValidator.isACorrectDate("29-02-2000"));
	}

}
