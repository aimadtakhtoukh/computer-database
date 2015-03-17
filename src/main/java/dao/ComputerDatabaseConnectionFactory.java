package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum ComputerDatabaseConnectionFactory {
	INSTANCE;

	private String url;
	private Properties properties;	

	private ComputerDatabaseConnectionFactory() {
		InputStream inputstream = null;
		properties = new Properties();
		try {
			inputstream = new FileInputStream("src/main/resources/db.properties");
			properties.load(inputstream);
			url = properties.getProperty("url");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputstream != null) {
				try {
					inputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static ComputerDatabaseConnectionFactory getInstance() {
		return INSTANCE;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, properties);
	}

}
