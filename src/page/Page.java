package page;

/**
 * This interface shows, for a list of elements, a paginated list of said elements.
 * Commands are given to navigate through it.
 * @author excilys
 *
 */

public interface Page {
	
	static final int ELEMENTS_PER_PAGE = 10;
	
	/**
	 * Prints the content of the page.
	 */
	
	void writeCurrentPage();
	
	/**
	 * Goes to the next page. It changes the content written by writeCurrentPage().
	 * Doesn't go beyond the last page.
	 */
	
	void goToNextPage();
	
	/**
	 * Goes to the previous page. It changes the content written by writeCurrentPage().
	 * Coesn't go beyond the first page.
	 */
	
	void goToPreviousPage();

}
