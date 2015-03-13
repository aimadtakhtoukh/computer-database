package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ComputerDatabaseConnectionFactory {
	
	private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String LOGIN = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	
	private ComputerDatabaseConnectionFactory() {}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, LOGIN, PASSWORD);
	}

}
