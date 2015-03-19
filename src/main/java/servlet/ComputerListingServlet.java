package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.Page;
import beans.Computer;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;

/**
 * Servlet implementation class ComputerListingServlet
 */
@WebServlet("/computer")
public class ComputerListingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		Page<ComputerDAO, Computer> page = new Page<ComputerDAO, Computer>(ComputerDAOImpl.getInstance());
		request.setAttribute("computerCount", page.getTotalCount());	
		request.setAttribute("items", page.getPageElements());
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
		rd.forward(request, response);
	}

}
