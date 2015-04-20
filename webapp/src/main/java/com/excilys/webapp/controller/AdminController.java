package com.excilys.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

	@RequestMapping(value="/admin")
	public ModelAndView adminView() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Vos papiers s'il vous plait");
		model.setViewName("admin");
		return model;
	}
	
}
