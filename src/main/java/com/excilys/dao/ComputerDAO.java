package com.excilys.dao;

import java.sql.Connection;

import com.excilys.beans.Computer;

/**
 * This Database Access Object interface defines the CRUD operations
 * for Computer beans.
 * @author excilys
 *
 */

public interface ComputerDAO extends CRUDDAO<Computer> {
	
	default void deleteByCompanyId(long companyId) {
		throw new UnsupportedOperationException("Not implemented yet");
	};
	
	default void deleteByCompanyId(long companyId, Connection conn) {
		throw new UnsupportedOperationException("Not implemented yet");
	};
	
}
