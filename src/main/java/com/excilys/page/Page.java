package com.excilys.page;

import java.util.Comparator;
import java.util.List;

public interface Page<T> {

	public int getLimit();
	
	public void setLimit(int limit);
	
	public int getOffset();
	
	public void setOffset(int offset);
	
	public int getTotalCount();
	
	public int getCurrentPageNumber();
	
	public int getTotalPageNumber();
	
	public List<T> getPageElements();
	
	public List<T> getAllElements();
	
	public void goToPage(int i);
	
	public Comparator<? super T> getComparator();
	
	public boolean isAscendent();
	
	public void setComparator(Comparator<? super T> comparator, boolean ascendent);
	
	public String getSearchString();
	
	public void setSearchString(String searchString);
	
	public void order();
	
}
