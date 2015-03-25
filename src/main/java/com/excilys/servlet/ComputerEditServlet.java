package com.excilys.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import com.excilys.beans.Company;
import com.excilys.beans.Computer;
import com.excilys.services.CompanyService;
import com.excilys.services.CompanyServiceImpl;
import com.excilys.services.ComputerService;
import com.excilys.services.ComputerServiceImpl;
import com.excilys.validator.DateValidator;
import com.excilys.validator.NumberValidator;
import com.excilys.validator.StringValidator;

/**
 * Servlet implementation class ComputerEditServlet
 */
@WebServlet("/editComputer")
public class ComputerEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	final Logger logger = LoggerFactory.getLogger(ComputerEditServlet.class);

	private ComputerService computerService = ComputerServiceImpl.getInstance();
	private CompanyService companyService = CompanyServiceImpl.getInstance();
	
	private DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerEditServlet() {
        super();
        sdf.setLenient(true);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.trace("GET called on /editComputer : Showing computer edit page, start up");
		String idParameter = request.getParameter("id");
		if (!NumberValidator.isARightNumber(idParameter)) {
			logger.trace("id isn't a number.");
			response.sendRedirect("dashboard");
			return;
		}
		long id = Long.parseLong(idParameter);
		Computer computer = computerService.getComputer(id);
		request.setAttribute("computerId", computer.getId());
		request.setAttribute("computerName", computer.getName());
		if (computer.getIntroduced() != null) {
			request.setAttribute("computerIntroduced", getFormattedTime(computer.getIntroduced().toLocalDate()));
		}
		if (computer.getDiscontinued() != null) { 
			request.setAttribute("computerDiscontinued", getFormattedTime(computer.getDiscontinued().toLocalDate()));
		}
		if (computer.getCompany() != null) {
			request.setAttribute("companySelectedId", computer.getCompany().getId());
		} else {
			request.setAttribute("companySelectedId", 0);
		}
		request.setAttribute("companies", companyService.getAllCompanies());
		logger.trace("GET called on /editComputer : Showing computer edit page, response sent");
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.trace("POST called on /editComputer : Editing computer, start up");
		String computerIdParameter = request.getParameter("computerId");
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		Computer computer = new Computer();
		if (NumberValidator.isARightNumber(computerIdParameter)) {
			computer.setId(Long.parseLong(computerIdParameter));
		} else {
			logger.trace("id isn't a number.");
			response.sendRedirect("dashboard");			
			return;	
		}
		
		if (StringValidator.isARightString(computerName)) {
			computer.setName(computerName);
		} else {
			logger.trace("name isn't a right string.");
			response.sendRedirect("dashboard");
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
		computerService.updateComputer(computer);
		logger.trace("POST called on /editComputer : Editing computer, done. Back to computer edit page.");
		response.sendRedirect("dashboard");
	}
	
	private String getFormattedTime(LocalDate date) {
		return new StringBuilder()
					.append(date.getDayOfMonth())
					.append("-")
					.append(date.getMonthValue())
					.append("-")
					.append(date.getYear())
					.toString();
	}

}
