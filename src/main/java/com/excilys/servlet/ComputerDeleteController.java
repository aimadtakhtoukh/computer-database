package com.excilys.servlet;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.services.ComputerService;
import com.excilys.validator.StringValidator;

@Controller
@RequestMapping("/deleteComputer")
public class ComputerDeleteController {
	
	final Logger logger = LoggerFactory.getLogger(ComputerDeleteController.class);

	@Autowired
	private ComputerService computerService;
	
	@RequestMapping(method = RequestMethod.POST)
	public String deleteComputers(@RequestParam String selection, Model model) {
		logger.trace("POST called on /dashboard : deleting computers, start up");
		if (!StringValidator.isARightString(selection)) {
			logger.trace("Empty selection");
			return "redirect:/dashboard";
		}
		Pattern idPattern = Pattern.compile("(\\d*,?)*");
		Matcher idMatcher = idPattern.matcher(selection);
		if (!idMatcher.matches()) {
			logger.trace("Wrong selection");
			return "redirect:/dashboard";
		}
		StringTokenizer st = new StringTokenizer(selection, ",");
		while (st.hasMoreTokens()) {
			computerService.deleteComputer(Long.parseLong(st.nextToken()));
		}		
		logger.trace("POST called on /dashboard : deleting computers, finished");		
		return "redirect:/dashboard";
	}
}
