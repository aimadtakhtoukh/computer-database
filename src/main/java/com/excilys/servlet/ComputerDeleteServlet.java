package com.excilys.servlet;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.services.ComputerService;

/**
 * Servlet implementation class ComputerDeleteServlet
 */
@Component
@WebServlet("/deleteComputer")
public class ComputerDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	final Logger logger = LoggerFactory.getLogger(ComputerDeleteServlet.class);

	@Autowired
	private ComputerService computerService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerDeleteServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.trace("POST called on /dashboard : deleting computers, start up");
		String ids = request.getParameter("selection");
		Pattern idPattern = Pattern.compile("(\\d*,?)*");
		Matcher idMatcher = idPattern.matcher(ids);
		if (!idMatcher.matches()) {
			response.sendRedirect("dashboard");
			return;
		}
		StringTokenizer st = new StringTokenizer(ids, ",");
		while (st.hasMoreTokens()) {
			computerService.deleteComputer(Long.parseLong(st.nextToken()));
		}
		logger.trace("POST called on /dashboard : deleting computers, finished");
		response.sendRedirect("dashboard");
	}

}
