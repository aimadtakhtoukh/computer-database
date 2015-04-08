package com.excilys.servlet;

import java.io.IOException;
import java.text.DateFormat;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import com.excilys.services.CompanyService;
import com.excilys.services.ComputerService;
import com.excilys.validator.DateValidator;
import com.excilys.validator.NumberValidator;
import com.excilys.validator.StringValidator;

/**
 * Servlet implementation class ComputerAddServlet
 */
@Component
@WebServlet("/addComputer")
public class ComputerAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	final Logger logger = LoggerFactory.getLogger(ComputerAddServlet.class);

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	
	private DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerAddServlet() {
        super();
        sdf.setLenient(true);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.trace("GET called on /addComputer : Showing computer add page, start up");
		request.setAttribute("show", false);
		request.setAttribute("companies", companyService.getAllCompanies());
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp");
		rd.forward(request, response);
		logger.trace("GET called on /addComputer : Showing computer add page, response sent");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.trace("POST called on /addComputer : Adding new computer, start up");
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp");
		
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
			Company company = companyService.getCompany(companyLongId);
			if (company != null) {
				computer.setCompany(company);
			}
		}
		computerService.createComputer(computer);
		request.setAttribute("companies", companyService.getAllCompanies());
		request.setAttribute("show", true);
		request.setAttribute("showSuccess", true);
		request.setAttribute("message", "Ordinateur ajouté. " + computer.toString());
		rd.forward(request, response);
		logger.trace("POST called on /addComputer : Adding new computer, done. Back to computer edit page.");
	}
}
