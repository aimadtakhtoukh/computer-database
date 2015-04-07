package com.excilys.dao;

import java.sql.Connection;
import java.util.List;

public interface CRUDDAO<T> {
	
	default T get(long id) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	default List<T> getAll() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	default List<T> getAll(int offset, int limit, String orderBy, boolean ascendant, String searchString) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	default int getCount() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	default long create(T t) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	default long update(long id, T t) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	default long delete(long id) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	default long create(T t, Connection conn) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	default long update(long id, T t, Connection conn) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	default long delete(long id, Connection conn) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
