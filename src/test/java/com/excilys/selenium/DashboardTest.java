package com.excilys.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DashboardTest {
	
	private static WebDriver driver = null;
	
	@BeforeClass
	public void setUp() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void theDashboardShouldShowSomething() {
		driver.get("http://localhost:8080/dashboard");
		List<WebElement> tables = driver.findElements(By.tagName("table"));
		System.out.println(tables);
		Assert.assertFalse(tables.isEmpty());
	}

}
