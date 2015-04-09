package com.excilys.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.services.ComputerService;

@Controller
public class ComputerController {
	
	@Autowired
	private ComputerService computerService;
	
	@RequestMapping("/dashboard")
	public String showDashboard(Model model) {
		System.out.println(computerService.getAll());
		return "dashboard";
	}

}
