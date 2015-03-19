package page;

import java.util.List;

import dao.CRUDDAO;

/**
 * The Page bean contains variables to define a page, by its offset,
 * the element-by-page limit, and the total number of pages.
 * @author excilys
 *
 */
public class Page<D extends CRUDDAO<T>, T> {
	
	private final static int STANDARD_PAGE_LIMIT = 10;
	private final static int STANDARD_PAGE_OFFSET = 0;
	
	private CRUDDAO<T> dao;
	private int limit;
	private int offset;
	private int total;
	
	public Page(CRUDDAO<T> dao) {
		super();
		if (dao == null) {
			throw new IllegalArgumentException("No DAO.");
		}
		this.dao = dao;
		this.limit = STANDARD_PAGE_LIMIT;
		this.offset = STANDARD_PAGE_OFFSET;
		this.total = dao.getCount();
	}
	
	public Page(CRUDDAO<T> dao, int limit, int offset) {
		this(dao);
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
			if (this.offset + limit < total) {
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
		return dao.getAll(offset, limit);
	}
	
	public void goToPage(int i) {
		setOffset(getLimit() * i);
	}
}
