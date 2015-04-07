package com.excilys.dao.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import com.excilys.dao.ComputerDAOImplTest;
import com.excilys.dao.ComputerDatabaseConnectionFactory;


public class DatabaseTestUtil {
	public static IDatabaseTester databaseTester;
	public static String jdbcDriver;
	public static String jdbcUrl;
	public static String user;
	public static String password;

	public static void setUpDatabase() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		final Properties properties = new Properties();
		final InputStream propertiesIs = DatabaseTestUtil.class
				.getClassLoader().getResourceAsStream("./db.properties");
		try {
			properties.load(propertiesIs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		jdbcDriver = properties.getProperty("driver");
		jdbcUrl = properties.getProperty("jdbcUrl");
		user = properties.getProperty("username");
		password = properties.getProperty("password");
		try {
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
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static void cleanlyInsert(IDataSet dataSet) throws Exception {
		databaseTester = new JdbcDatabaseTester(
				jdbcDriver, jdbcUrl, user, password);
		databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}
}
