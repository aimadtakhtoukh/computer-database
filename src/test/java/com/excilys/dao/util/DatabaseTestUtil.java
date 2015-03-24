package com.excilys.dao.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;


public class DatabaseTestUtil {
	public static IDatabaseTester databaseTester;
	public static String jdbcDriver;
	public static String jdbcUrl;
	public static String user;
	public static String password;

	static {
		final Properties properties = new Properties();
		final InputStream is = DatabaseTestUtil.class
				.getClassLoader().getResourceAsStream("./db.properties");
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		jdbcDriver = properties.getProperty("driver");
		jdbcUrl = properties.getProperty("url");
		user = properties.getProperty("user");
		password = properties.getProperty("password");
	}

	public static void cleanlyInsert(IDataSet dataSet) throws Exception {
		databaseTester = new JdbcDatabaseTester(
				jdbcDriver, jdbcUrl, user, password);
		databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}
}
