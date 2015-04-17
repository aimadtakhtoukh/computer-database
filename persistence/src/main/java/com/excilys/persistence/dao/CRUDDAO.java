package com.excilys.persistence.dao;

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

}
