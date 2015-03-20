package servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.Page;
import servlet.dto.ComputerDTO;
import beans.Computer;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;

/**
 * Servlet implementation class ComputerListingServlet
 */
@WebServlet("/dashboard")
public class ComputerListingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int PAGE_NUMBER = 5;
	
	private Page<ComputerDAO, Computer> page = new Page<ComputerDAO, Computer>(ComputerDAOImpl.getInstance());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerListingServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentResultsPerPage = verifyCurrentResultsPerPageParameter(request.getParameter("resultsPerPage"));
		page.setLimit(currentResultsPerPage);
		int currentPage = verifyCurrentPageParameter(request.getParameter("page"));
		page.goToPage(currentPage);
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
	}
	
	private int verifyCurrentPageParameter(String param) {
		if (param == null) {
			return 0;
		}
		int result = Integer.parseInt(param);
		if (result < 1) {return 0;}
		if (result > page.getTotalPageNumber()) {return page.getTotalPageNumber();}
		return result - 1;
	}
	
	private int verifyCurrentResultsPerPageParameter(String param) {
		if (param == null) {
			return 10;
		}
		int result = Integer.parseInt(param);
		if (result < 1) {return 10;}
		if (result > page.getTotalCount()) {return page.getTotalCount();}
		return result;
	}

}
