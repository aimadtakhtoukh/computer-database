package com.excilys.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public enum ComputerDatabaseConnectionFactory {
	INSTANCE;

	private BoneCP pool;

	private ComputerDatabaseConnectionFactory() {
		/*
		try {
			Driver monDriver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(monDriver);
		} catch (SQLException e1) {
			throw new PersistenceException("Erreur de chargement de classe", e1);
		}
		*/
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try (InputStream inputstream = cl.getResourceAsStream("./db.properties");) {
			Properties properties = new Properties();
			properties.load(inputstream);
			Class.forName(properties.getProperty("driver")).newInstance();
			BoneCPConfig config = new BoneCPConfig(properties);
            pool = new BoneCP(config);
		} catch (IOException e) {
			throw new PersistenceException("Erreur de chargement du fichier de propriété", e);
		} catch (Exception e) {
			throw new PersistenceException("Erreur de chargement de la configuration de BoneCP.", e);
		}
	}

	public static ComputerDatabaseConnectionFactory getInstance() {
		return INSTANCE;
	}

	public Connection getConnection() {
		try {
			return pool.getConnection();
		} catch (SQLException e) {
			throw new PersistenceException("Impossible to get a connection. ", e);
		}
	}
	
	public static void cleanConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}
	}
	
	public static void cleanAfterConnection(ResultSet rs, Statement s) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}
		if (s != null) {
			try {
				s.close();
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}
	}


}
