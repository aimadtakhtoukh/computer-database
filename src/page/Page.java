package page;

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
	
	private List<T> elements;
	private int limit;
	private int offset;
	private int pageTotal;
	
	public Page(List<T> elements) {
		super();
		if (elements == null) {
			throw new IllegalArgumentException("No elements.");
		}
		if (elements.isEmpty()) {
			throw new IllegalArgumentException("No elements.");
		}
		this.elements = elements;
		this.limit = STANDARD_PAGE_LIMIT;
		this.offset = STANDARD_PAGE_OFFSET;
		pageTotal = (elements.size() / limit) + 1;
	}
	
	public Page(List<T> elements, int limit, int offset) {
		this(elements);
		this.limit = limit;
		this.offset = offset;
	}
	
	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		if (elements == null) {
			throw new IllegalArgumentException("No elements.");
		}
		if (elements.isEmpty()) {
			throw new IllegalArgumentException("No elements.");
		}
		this.elements = elements;
		pageTotal = (elements.size() / limit) + 1;
	}

	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
		pageTotal = (elements.size() / limit) + 1;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		if (offset < 0) {
			this.offset = 0;
		} else {
			if (this.offset + limit < elements.size()) {
				this.offset = offset;
			}
		}
	}
	
	public int getPageTotal() {
		return pageTotal;
	}
	
	public int getCurrentPageNumber() {
		return (offset / limit);
	}

	public List<T> getPageElements() {
		int higherBound = Math.min(offset + limit, elements.size());
		return elements.subList(offset, higherBound); 
	}
	
	public void goToPage(int i) {
		setOffset(getLimit() * i);
	}
}
