package com.excilys.cli;

import org.junit.Assert;
import org.junit.Test;

import com.excilys.validator.DateValidator;

public class DateVerifierTest {
	
	@Test
	public void test4Verifier() {
		Assert.assertTrue(DateValidator.isACorrectDate("31-01-1992"));
		Assert.assertFalse(DateValidator.isACorrectDate("31-02-1992"));
		Assert.assertTrue(DateValidator.isACorrectDate("31-03-1992"));
		Assert.assertFalse(DateValidator.isACorrectDate("31-04-1992"));
		Assert.assertTrue(DateValidator.isACorrectDate("31-05-1992"));
		Assert.assertFalse(DateValidator.isACorrectDate("31-06-1992"));
		Assert.assertTrue(DateValidator.isACorrectDate("31-07-1992"));
		Assert.assertTrue(DateValidator.isACorrectDate("31-08-1992"));
		Assert.assertFalse(DateValidator.isACorrectDate("31-09-1992"));
		Assert.assertTrue(DateValidator.isACorrectDate("31-10-1992"));
		Assert.assertFalse(DateValidator.isACorrectDate("31-11-1992"));
		Assert.assertTrue(DateValidator.isACorrectDate("31-12-1992"));
	}
	
	@Test
	public void test1Verifier() {
		Assert.assertTrue(DateValidator.isACorrectDate("29-02-1992"));
	}
	
	@Test
	public void test2Verifier() {
		Assert.assertFalse(DateValidator.isACorrectDate("29-02-1993"));
	}
	
	@Test
	public void test3Verifier() {
		Assert.assertTrue(DateValidator.isACorrectDate("29-02-2000"));
	}

}
