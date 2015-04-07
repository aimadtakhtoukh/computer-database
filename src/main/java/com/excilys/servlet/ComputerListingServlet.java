package com.excilys.servlet;

import java.io.IOException;
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
import com.excilys.page.ComputerPage;
import com.excilys.services.ComputerService;
import com.excilys.services.ComputerServiceImpl;
import com.excilys.servlet.dto.ComputerDTO;
import com.excilys.validator.NumberValidator;

/**
 * Servlet implementation class ComputerListingServlet
 */
@WebServlet("/dashboard")
public class ComputerListingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	final Logger logger = LoggerFactory.getLogger(ComputerListingServlet.class);
	
	private static final int PAGE_NUMBER = 5;

	private ComputerService computerService = ComputerServiceImpl.getInstance();
	private Map<String, String> orderByStrings = new HashMap<>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerListingServlet() {
        super();
        orderByStrings.put("id", "computer.id");
        orderByStrings.put("name", "computer.name");
        orderByStrings.put("introduced", "computer.introduced");
        orderByStrings.put("discontinued", "computer.discontinued");
        orderByStrings.put("company", "company.name");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.trace("GET called on /dashboard : Showing dashboard, start up");
		ComputerPage page = computerService.getComputerPage();
		int currentResultsPerPage = page.getLimit();
		if (request.getParameter("resultsPerPage") != null) {
			currentResultsPerPage = verifyCurrentResultsPerPageParameter(request.getParameter("resultsPerPage"));
			page.setLimit(currentResultsPerPage);
		}
		if (request.getParameter("page") != null) {
			int currentPage = verifyCurrentPageParameter(request.getParameter("page"));
			page.goToPage(currentPage);
		}
		if (request.getParameter("search") != null) {
			page.setSearchString(request.getParameter("search"));
		}
		
		boolean ascendentOrderBy = page.isAscendent();
		if (request.getParameter("asc") != null) {
			if (request.getParameter("asc").equals("true")) {
				ascendentOrderBy = true;
			}
			if (request.getParameter("asc").equals("false")) {
				ascendentOrderBy = false;
			}
		}
		String orderBy = request.getParameter("orderBy");
		if (orderBy != null) {
			if (orderByStrings.get(orderBy) != null) {
				page.setOrder(orderByStrings.get(orderBy), ascendentOrderBy);
			}
		}
		// Compte des ordinateurs
		request.setAttribute("computerCount", page.getTotalCount());
		// Ordinateurs de la page courante
		List<ComputerDTO> items = new LinkedList<ComputerDTO>();
		for (Computer c : page.getPageElements()) {
			items.add(new ComputerDTO(c));
		}
		request.setAttribute("items", items);
		// Variables de pagination
		int current = page.getCurrentPageNumber();
		int start = Math.max(1, current - PAGE_NUMBER);
		int finish = Math.min(current + PAGE_NUMBER, page.getTotalPageNumber() + 1);
		request.setAttribute("paginationStart", start);
		request.setAttribute("paginationFinish", finish);
		request.setAttribute("currentPageNumber", current + 1);
		request.setAttribute("totalPageNumber", page.getTotalPageNumber() + 1);
		request.setAttribute("resultsPerPage", currentResultsPerPage);
		// Variables de recherche
		request.setAttribute("search", request.getParameter("search"));
		request.setAttribute("orderBy", request.getParameter("orderBy"));
		request.setAttribute("asc", request.getParameter("asc"));
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
		rd.forward(request, response);
		logger.trace("GET called on /dashboard : Showing dashboard, response sent");
	}
	
	private int verifyCurrentPageParameter(String param) {
		if (NumberValidator.isARightNumber(param)) {
			int result = Integer.parseInt(param);
			if (result < 1) {return 0;}
			return result - 1;
		} else {
			return 1;
		}
	}
	
	private int verifyCurrentResultsPerPageParameter(String param) {
		if (NumberValidator.isARightNumber(param)) {
			int result = Integer.parseInt(param);
			if (result < 1) {return 10;}
			return result;
		} else {
			return 10;
		}
	}

}
