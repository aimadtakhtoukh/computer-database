package com.excilys.persistence.page;

import java.util.List;

import com.excilys.persistence.dao.CRUDDAO;

public interface Page<T> {
	
	public CRUDDAO<T> getDAO();

	public int getLimit();
	
	public void setLimit(int limit);
	
	public int getOffset();
	
	public void setOffset(int offset);
	
	public boolean isAscendent();
	
	public String getOrderedColumn();
	
	public void setOrder(String column, boolean ascendent);
	
	public String getSearchString();
	
	public void setSearchString(String searchString);
	
	public int getTotalCount();
	
	public int getCurrentPageNumber();
	
	public int getTotalPageNumber();
	
	public List<T> getPageElements();
	
	public void goToPage(int i);
	
}
