package com.excilys.dao;

import java.util.List;

public interface CRUDDAO<T> {
	
	long create(T t);
	
	long update(long id, T t);
	
	T get(long id);
	
	long delete(long id);
	
	List<T> getAll();
	
	List<T> getAll(int offset, int limit);
	
	int getCount();

}
