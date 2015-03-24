package com.excilys.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public enum ComputerDatabaseConnectionFactory {
	INSTANCE;

	private String url;
	private Properties properties;	

	private ComputerDatabaseConnectionFactory() {
		try {
			Driver monDriver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(monDriver);
		} catch (SQLException e1) {
			throw new PersistenceException("Erreur de chargement de classe", e1);
		}
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		
		try (InputStream inputstream = cl.getResourceAsStream("./db.properties");) {
			properties = new Properties();
			properties.load(inputstream);
			url = properties.getProperty("url");
			System.out.println(url);
		} catch (IOException e) {
			throw new PersistenceException("Erreur de chargement du fichier de propriété", e);
		}
	}

	public static ComputerDatabaseConnectionFactory getInstance() {
		return INSTANCE;
	}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection(url, properties);
		} catch (SQLException e) {
			throw new PersistenceException("Impossible to get a connection. ", e);
		}
	}
	
	public static void cleanAfterConnection(Connection conn, ResultSet rs, Statement s) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// Nothing to do.
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// Nothing to do.
			}
		}
		if (s != null) {
			try {
				s.close();
			} catch (SQLException e) {
				// Nothing to do.
			}
		}
	}

}