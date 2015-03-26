package com.excilys.page;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Page bean contains variables to define a page, by its offset,
 * the element-by-page limit, and the total number of pages.
 * @author excilys
 *
 */
public class PageImpl<T> implements Page<T> {
	
	final Logger logger = LoggerFactory.getLogger(PageImpl.class);
	
	private final static int STANDARD_PAGE_LIMIT = 10;
	private final static int STANDARD_PAGE_OFFSET = 0;
	
	private List<T> list;
	private int limit;
	private int offset;
	private int total;
	//Order by variables
	private Comparator<? super T> comparator;
	private boolean ascendent;
	//Search variables
	private String searchString;
	
	public PageImpl(List<T> list) {
		super();
		if (list == null) {
			throw new IllegalArgumentException("Empty list.");
		}
		this.list = list;
		this.limit = STANDARD_PAGE_LIMIT;
		this.offset = STANDARD_PAGE_OFFSET;
		this.total = list.size();
		logger.trace("Page created.");
	}
	
	public PageImpl(List<T> list, int limit, int offset) {
		this(list);
		this.limit = limit;
		this.offset = offset;
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
	
	public List<T> getPageElements() {
		logger.trace("Elements from " + offset + " to " + (offset + limit) + " sent.");
		return list.subList(Math.max(0, offset), Math.min(offset + limit, list.size()));
	}
	
	public List<T> getAllElements() {
		logger.trace("All elements sent.");
		return list;
	}
	
	public void goToPage(int i) {
		if (i >= 0 && i <= getTotalPageNumber()) {
			setOffset(getLimit() * i);
		}
		logger.trace("Page set to the " + i + "th page.");
	}
	
	public Comparator<? super T> getComparator() {
		return comparator;
	}
	
	public boolean isAscendent() {
		return ascendent;
	}
	
	public void setComparator(Comparator<? super T> comparator, boolean ascendent) {
		this.comparator = comparator;
		this.ascendent = ascendent;
	}
	
	public String getSearchString() {
		return searchString;
	}
	
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
	public void order() {
		if (comparator != null) {
			list.sort(comparator);
			if (!ascendent) {
				Collections.reverse(list);
			}
			logger.trace("Elements sorted.");
		} else {
			logger.trace("No comparator set for Page Object.");
		}
	}
}
