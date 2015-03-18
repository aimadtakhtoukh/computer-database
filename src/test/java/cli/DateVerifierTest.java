package cli;

import org.junit.Assert;
import org.junit.Test;

public class DateVerifierTest {
	
	@Test
	public void test4Verifier() {
		Assert.assertTrue(DateVerifier.isTheRightDate("31-01-1992"));
		Assert.assertFalse(DateVerifier.isTheRightDate("31-02-1992"));
		Assert.assertTrue(DateVerifier.isTheRightDate("31-03-1992"));
		Assert.assertFalse(DateVerifier.isTheRightDate("31-04-1992"));
		Assert.assertTrue(DateVerifier.isTheRightDate("31-05-1992"));
		Assert.assertFalse(DateVerifier.isTheRightDate("31-06-1992"));
		Assert.assertTrue(DateVerifier.isTheRightDate("31-07-1992"));
		Assert.assertTrue(DateVerifier.isTheRightDate("31-08-1992"));
		Assert.assertFalse(DateVerifier.isTheRightDate("31-09-1992"));
		Assert.assertTrue(DateVerifier.isTheRightDate("31-10-1992"));
		Assert.assertFalse(DateVerifier.isTheRightDate("31-11-1992"));
		Assert.assertTrue(DateVerifier.isTheRightDate("31-12-1992"));
	}
	
	@Test
	public void test1Verifier() {
		Assert.assertTrue(DateVerifier.isTheRightDate("29-02-1992"));
	}
	
	@Test
	public void test2Verifier() {
		Assert.assertFalse(DateVerifier.isTheRightDate("29-03-1993"));
	}
	
	@Test
	public void test3Verifier() {
		Assert.assertTrue(DateVerifier.isTheRightDate("29-02-2000"));
	}

}
