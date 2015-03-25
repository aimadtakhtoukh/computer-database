package com.excilys.page;

import java.util.Comparator;
import java.util.List;

/**
 * The Page bean contains variables to define a page, by its offset,
 * the element-by-page limit, and the total number of pages.
 * @author excilys
 *
 */
public class Page<T> {
	
	private final static int STANDARD_PAGE_LIMIT = 10;
	private final static int STANDARD_PAGE_OFFSET = 0;
	
	private List<T> list;
	private int limit;
	private int offset;
	private int total;
	
	public Page(List<T> list) {
		super();
		if (list == null) {
			throw new IllegalArgumentException("Empty list.");
		}
		this.list = list;
		this.limit = STANDARD_PAGE_LIMIT;
		this.offset = STANDARD_PAGE_OFFSET;
		this.total = list.size();
	}
	
	public Page(List<T> list, int limit, int offset) {
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
		return list.subList(offset, offset + limit);
	}
	
	public void goToPage(int i) {
		if (i >= 0 && i <= getTotalPageNumber()) {
			setOffset(getLimit() * i);
		}
	}
	
	public void sortList(Comparator<? super T> comparator) {
		list.sort(comparator); 
	}
}
