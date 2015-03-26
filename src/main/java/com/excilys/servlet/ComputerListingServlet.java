package com.excilys.servlet;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.beans.Computer;
import com.excilys.page.PageImpl;
import com.excilys.page.comparators.ComputerComparator;
import com.excilys.services.ComputerService;
import com.excilys.services.ComputerServiceImpl;
import com.excilys.servlet.dto.ComputerDTO;

/**
 * Servlet implementation class ComputerListingServlet
 */
@WebServlet("/dashboard")
public class ComputerListingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	final Logger logger = LoggerFactory.getLogger(ComputerListingServlet.class);
	
	private static final int PAGE_NUMBER = 5;

	private ComputerService computerService = ComputerServiceImpl.getInstance();
	private PageImpl<Computer> page = computerService.getComputerPage();
	
	// Order By variables
	private Map<String, Comparator<Computer>> comparators = new HashMap<>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerListingServlet() {
        super();
        comparators.put("id", ComputerComparator.COMPARE_BY_ID);
        comparators.put("name", ComputerComparator.COMPARE_BY_NAME);
        comparators.put("introduced", ComputerComparator.COMPARE_BY_INTRODUCED_DATE);
        comparators.put("discontinued", ComputerComparator.COMPARE_BY_DISCONTINUED_DATE);
        comparators.put("company", ComputerComparator.COMPARE_BY_COMPANY);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.trace("GET called on /dashboard : Showing dashboard, start up");
		if (page.getTotalCount() != computerService.getComputerPage().getTotalCount()) {
			PageImpl<Computer> newpage = computerService.getComputerPage();
			newpage.setLimit(page.getLimit());
			newpage.setOffset(page.getOffset());
			page = newpage;
		}
		int currentResultsPerPage = page.getLimit();
		if (request.getParameter("resultsPerPage") != null) {
			currentResultsPerPage = verifyCurrentResultsPerPageParameter(request.getParameter("resultsPerPage"));
			page.setLimit(currentResultsPerPage);
		}
		if (request.getParameter("page") != null) {
			int currentPage = verifyCurrentPageParameter(request.getParameter("page"));
			page.goToPage(currentPage);
		}
		boolean ascendantOrderBy = page.isAscendent();
		if (request.getParameter("asc") != null) {
			if (request.getParameter("asc").equals("true")) {
				ascendantOrderBy = true;
			}
			if (request.getParameter("asc").equals("false")) {
				ascendantOrderBy = false;
			}
		}
		if (request.getParameter("orderBy") != null) {
			Comparator<Computer> comparator = comparators.get(request.getParameter("orderBy"));
			if (comparator != null) {
				page.setComparator(comparator, ascendantOrderBy);
			}
		}
		page.order();
		// Compte des ordinateurs
		request.setAttribute("computerCount", page.getTotalCount());
		// Ordinateurs de la page courante
		List<ComputerDTO> items = new LinkedList<ComputerDTO>();
		for (Computer c : page.getPageElements()) {
			items.add(new ComputerDTO(c));
		}
		request.setAttribute("items", items);
		// Pages proches
		int current = page.getCurrentPageNumber();
		int start = Math.max(1, current - PAGE_NUMBER);
		int finish = Math.min(current + PAGE_NUMBER, page.getTotalPageNumber() + 1);
		request.setAttribute("paginationStart", start);
		request.setAttribute("paginationFinish", finish);
		request.setAttribute("currentPageNumber", current + 1);
		request.setAttribute("totalPageNumber", page.getTotalPageNumber() + 1);
		request.setAttribute("resultsPerPage", currentResultsPerPage);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
		rd.forward(request, response);
		logger.trace("GET called on /dashboard : Showing dashboard, response sent");
	}
	
	private int verifyCurrentPageParameter(String param) {
		int result = Integer.parseInt(param);
		if (result < 1) {return 0;}
		if (result > page.getTotalPageNumber()) {return page.getTotalPageNumber();}
		return result - 1;
	}
	
	private int verifyCurrentResultsPerPageParameter(String param) {
		int result = Integer.parseInt(param);
		if (result < 1) {return 10;}
		if (result > page.getTotalCount()) {return page.getTotalCount();}
		return result;
	}

}
