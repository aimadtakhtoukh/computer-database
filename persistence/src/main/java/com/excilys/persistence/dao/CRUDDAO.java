package com.excilys.persistence.dao;

import java.util.List;

public interface CRUDDAO<T> {
	/**
	 * Gets a bean from the database.
	 * @param id
	 * @return
	 */
	default T get(long id) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Returns all beans from the database.
	 * @return
	 */	
	default List<T> getAll() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Returns all beans from the database, from offset to offset + limit,
	 * ordered by the column described by orderBy, ascendant if the
	 * ascendant boolean is true, and filtered by the String in the searchString argument.
	 * @param offset
	 * @param limit
	 * @param orderBy
	 * @param ascendant
	 * @param searchString
	 * @return
	 */
	default List<T> getAll(int offset, int limit, String orderBy, boolean ascendant, String searchString) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Returns the count of all beans in the database.
	 * @return
	 */
	default int getCount() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Adds a bean in the database.
	 * @param t
	 * @return
	 */
	default long create(T t) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Update the bean with the same id than the id parameter.
	 * @param id
	 * @param t
	 * @return
	 */
	default long update(long id, T t) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/**
	 * Delete the bean with the same id than the id parameter.
	 * @param id
	 * @return
	 */
	default long delete(long id) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
