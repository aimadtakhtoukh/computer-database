package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ComputerDatabaseConnectionFactory {
	
	private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	private static final String LOGIN = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ComputerDatabaseConnectionFactory() {}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, LOGIN, PASSWORD);
	}

}
