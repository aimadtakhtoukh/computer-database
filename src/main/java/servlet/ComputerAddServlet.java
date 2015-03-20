package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CompanyDAO;
import dao.CompanyDAOImpl;

/**
 * Servlet implementation class ComputerAddServlet
 */
@WebServlet("/addComputer")
public class ComputerAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CompanyDAO companyDAO = CompanyDAOImpl.getInstance();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerAddServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("companies", companyDAO.getAll());
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp");
		rd.forward(request, response);
	}

}
