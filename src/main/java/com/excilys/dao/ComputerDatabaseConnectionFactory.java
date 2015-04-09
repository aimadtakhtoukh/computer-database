package com.excilys.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ComputerDatabaseConnectionFactory {

	private ThreadLocal<Connection> localConnection = new ThreadLocal<Connection>();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Connection getConnection() {
		if (localConnection.get() == null) {
			try {
				localConnection.set(jdbcTemplate.getDataSource().getConnection());
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
