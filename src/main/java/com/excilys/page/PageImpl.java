package com.excilys.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.CRUDDAO;

/**
 * The Page bean contains variables to define a page, by its offset,
 * the element-by-page limit, and the total number of pages.
 * @author excilys
 *
 */
public abstract class PageImpl<T> implements Page<T> {
	
	final Logger logger = LoggerFactory.getLogger(PageImpl.class);
	
	private final static int STANDARD_PAGE_LIMIT = 10;
	private final static int STANDARD_PAGE_OFFSET = 0;
	
	private CRUDDAO<T> dao;
	private int limit;
	private int offset;
	private int total;
	//Order by variables
	private String column;
	private boolean ascendent;
	//Search variables
	private String searchString;
	
	public PageImpl(CRUDDAO<T> dao) {
		super();
		if (dao == null) {
			throw new IllegalArgumentException("Null DAO.");
		}
		this.dao = dao;
		this.limit = STANDARD_PAGE_LIMIT;
		this.offset = STANDARD_PAGE_OFFSET;
		this.total = dao.getCount();
		logger.trace("Page created.");
	}
	
	public PageImpl(CRUDDAO<T> dao, int limit, int offset, String order) {
		this(dao);
		this.limit = limit;
		this.offset = offset;
	}
	
	public CRUDDAO<T> getDAO() {
		return dao;
	}

	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		if (limit < 0) {
			limit = 0;
		}
		this.limit = limit;
		logger.trace("Limit set to " + this.limit + ".");
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		if (offset < 0) {
			this.offset = 0;
		} else {
			if (offset + limit <= (total + (limit - (total % limit)))) {
				this.offset = offset;
			}
		}
		logger.trace("Offset set to " + this.offset + ".");
	}
	
	public int getTotalCount() {
		return total;
	}
	
	public int getCurrentPageNumber() {
		if (limit == 0) {
			return offset;
		}
		return (offset / limit);
	}
	
	public int getTotalPageNumber() {
		if (limit == 0) {
			return total;
		}
		return (total / limit);
	}
	
	/*
	public List<T> getPageElements() {
		logger.trace("Elements from " + offset + " to " + (offset + limit) + " sent.");
		return list.subList(Math.max(0, offset), Math.min(offset + limit, list.size()));
	}
	
	public List<T> getAllElements() {
		logger.trace("All elements sent.");
		return list;
	}
	*/
	
	
	public void goToPage(int i) {
		if (i >= 0 && i <= getTotalPageNumber()) {
			setOffset(getLimit() * i);
		}
		logger.trace("Page set to the " + i + "th page.");
	}
	/*
	public Comparator<? super T> getComparator() {
		return comparator;
	}
	*/

	public String getOrderedColumn() {
		return column;
	}
	
	public boolean isAscendent() {
		return ascendent;
	}
	
	public String getSearchString() {
		return searchString;
	}
	
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
