package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import validator.DateValidator;
import validator.NumberValidator;
import validator.StringValidator;
import beans.Company;
import beans.Computer;
import dao.CompanyDAO;
import dao.CompanyDAOImpl;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;

/**
 * Servlet implementation class ComputerAddServlet
 */
@WebServlet("/addComputer")
public class ComputerAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private ComputerDAO computerDAO = ComputerDAOImpl.getInstance();
	private CompanyDAO companyDAO = CompanyDAOImpl.getInstance();
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
	
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
		request.setAttribute("show", false);
		request.setAttribute("companies", companyDAO.getAll());
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp");
		request.setAttribute("companies", companyDAO.getAll());
		
		Computer computer = new Computer();
		if (StringValidator.isARightString(computerName)) {
			computer.setName(computerName);
		} else {
			request.setAttribute("show", true);
			request.setAttribute("showSuccess", false);
			request.setAttribute("message", "Problème avec le nom de l'ordinateur. Est-il vide?");
			rd.forward(request, response);
			return;			
		}
		if (DateValidator.isTheRightDate(introduced)) {
			try {
				computer.setIntroduced(LocalDateTime.ofInstant(sdf.parse(introduced).toInstant(), ZoneId.systemDefault()));
			} catch (ParseException e) {
				computer.setIntroduced(null);
			}			
		}
		if (DateValidator.isTheRightDate(discontinued)) {
			try {
				computer.setDiscontinued(LocalDateTime.ofInstant(sdf.parse(discontinued).toInstant(), ZoneId.systemDefault()));
			} catch (ParseException e) {
				computer.setDiscontinued(null);
			}			
		}
		if (NumberValidator.isARightNumber(companyId)) {
			long companyLongId = Long.parseLong(companyId);
			Company company = companyDAO.get(companyLongId);
			if (company != null) {
				computer.setCompany(company);
			}
		}
		
		long id = computerDAO.create(computer);
		computer.setId(id);

		request.setAttribute("show", true);
		request.setAttribute("showSuccess", true);
		request.setAttribute("message", "Ordinateur ajouté. " + computer.toString());
		rd.forward(request, response);
	}
}
