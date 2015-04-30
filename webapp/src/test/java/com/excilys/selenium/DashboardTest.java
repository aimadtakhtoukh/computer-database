package com.excilys.selenium;


public class DashboardTest {
	/*
	private static WebDriver driver = null;
	
	@BeforeClass
	public static void setUp() throws DataSetException, Exception {
		prepareTestBase();
		driverLaunch();
	}

	private static void prepareTestBase() throws DataSetException, Exception {
		DatabaseTestUtil.setUpDatabase();
	}
	
	private static void driverLaunch() {
		driver = new FirefoxDriver();
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Before
	public void install() throws Exception {
		//GIVEN
		DatabaseTestUtil.cleanlyInsert(
				new FlatXmlDataSetBuilder().build(new File(
                "src/test/resources/datasets/general/get.xml")));
	}
	
	@After
	public void cleanup() throws Exception {
        DatabaseTestUtil.databaseTester.onTearDown();
	}
	
	@Test
	@Ignore
	public void theDashboardShouldShowSomething() {
		driver.get("http://localhost:8080/dashboard");
		List<WebElement> tables = driver.findElements(By.tagName("table"));
		System.out.println(tables);
		Assert.assertFalse(tables.isEmpty());
	}
	*/
}
