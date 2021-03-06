package com.excilys.persistence.dao.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import com.excilys.persistence.dao.ComputerDAOImplTest;

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
		jdbcDriver = properties.getProperty("jdbc.driver");
		jdbcUrl = properties.getProperty("jdbc.url");
		user = properties.getProperty("jdbc.username");
		password = properties.getProperty("jdbc.password");
	}

	public static void cleanlyInsert(IDataSet dataSet) throws Exception {
		databaseTester = new JdbcDatabaseTester(
				jdbcDriver, jdbcUrl, user, password);
		databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}
	
	public static void executeSqlFile(String file, Connection connection) throws IOException, SQLException {
		final InputStream is = ComputerDAOImplTest.class
				.getClassLoader().getResourceAsStream("test.sql");
		try (final BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			final StringBuilder sb = new StringBuilder();
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str + "\n ");
			}
			try (final Statement stmt = connection.createStatement()) {
				stmt.execute(sb.toString());
			}
		}
	}
}
