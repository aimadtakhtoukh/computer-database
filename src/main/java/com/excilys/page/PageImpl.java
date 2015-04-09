package com.excilys.page;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.dao.CRUDDAO;
import com.excilys.dao.ComputerDatabaseConnectionFactory;

/**
 * The Page bean contains variables to define a page, by its offset,
 * the element-by-page limit, and the total number of pages.
 * @author excilys
 *
 */
public abstract class PageImpl<T> implements Page<T> {
	
	final Logger logger = LoggerFactory.getLogger(PageImpl.class);
	
	@Autowired
	private ComputerDatabaseConnectionFactory cdcf;
	
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
			throw new IllegalArgumentException("DAO is null.");
		}
		this.dao = dao;
		this.limit = STANDARD_PAGE_LIMIT;
		this.offset = STANDARD_PAGE_OFFSET;
		this.total = dao.getCount();
		logger.trace("Page created.");
	}
	
	public PageImpl(CRUDDAO<T> dao, int limit, int offset) {
		this(dao);
		this.limit = limit;
		this.offset = offset;
	}
	
	public CRUDDAO<T> getDAO() {
		return dao;
	}
	
	public List<T> getPageElements() {
		List<T> result = dao.getAll(getOffset(), getLimit(), getOrderedColumn(), isAscendent(), getSearchString());
		return result;
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
	
	public void goToPage(int i) {
		if (i >= 0 && i <= getTotalPageNumber()) {
			setOffset(getLimit() * i);
		}
		logger.trace("Page set to the " + i + "th page.");
	}

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

	public void setOrder(String column, boolean ascendent) {
		this.column = column;
		this.ascendent = ascendent;
	}
}
