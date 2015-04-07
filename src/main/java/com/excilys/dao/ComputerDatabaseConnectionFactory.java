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
	private ThreadLocal<Connection> localConnection = new ThreadLocal<Connection>();

	private ComputerDatabaseConnectionFactory() {
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
		if (localConnection.get() == null) {
			try {
				localConnection.set(pool.getConnection());
			} catch (SQLException e) {
				throw new PersistenceException("Impossible to get a connection. ", e);
			}
		}
		return localConnection.get();
	}
	
	public void cleanConnection() {
		if (localConnection.get() != null) {
			try {
				localConnection.get().close();
				localConnection.remove();
			} catch (SQLException e) {
				throw new PersistenceException("Impossible to delete the connection. ", e);
			}
		}		
	}
	
	public static void releaseRessources(ResultSet rs, Statement s) {
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
	
	public void commit() {
		try {
			localConnection.get().commit();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	public void rollback() {
		try {
			localConnection.get().rollback();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

}
