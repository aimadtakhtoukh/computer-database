package com.excilys.service.services;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-service.xml")
public class CompanyServiceImplTest {
	/*
	@Autowired
	ComputerDatabaseConnectionFactory cdcf;
	@Autowired
	CompanyService companyService;
	
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
	public void DeleteCompanyAndRealtedComputersDoesAsAdvertised() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/general/get.xml")));
		//WHEN
		companyService.deleteCompanyAndRelatedComputers(1);
		//THEN
		Assert.assertEquals(companyService.getCompany(1), null);
	}
	*/
}
