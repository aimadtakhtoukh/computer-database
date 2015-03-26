package com.excilys.services;

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

import com.excilys.dao.ComputerDAOImplTest;
import com.excilys.dao.ComputerDatabaseConnectionFactory;
import com.excilys.dao.util.DatabaseTestUtil;

public class CompanyServiceImplTest {
	
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
	public void DeleteCompanyAndRealtedComputersDoesAsAdvertised() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/general/get.xml")));
		CompanyService companyService = CompanyServiceImpl.getInstance();
		//WHEN
		companyService.deleteCompanyAndRelatedComputers(1);
		//THEN
		Assert.assertEquals(companyService.getCompany(1), null);
	}
	
}
