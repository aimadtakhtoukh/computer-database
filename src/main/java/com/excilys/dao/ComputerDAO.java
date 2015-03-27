package com.excilys.dao;

import java.sql.Connection;
import java.util.List;

import com.excilys.beans.Computer;

/**
 * This Database Access Object interface defines the CRUD operations
 * for Computer beans.
 * @author excilys
 *
 */

public interface ComputerDAO extends CRUDDAO<Computer> {
	
	default List<Computer> getAll(int offset, int limit, String orderBy, boolean ascendant, String searchString) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	default void deleteByCompanyId(long companyId) {
		throw new UnsupportedOperationException("Not implemented yet");
	};
	
	default void deleteByCompanyId(long companyId, Connection conn) {
		throw new UnsupportedOperationException("Not implemented yet");
	};
	
}
