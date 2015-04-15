package com.excilys.servlet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.beans.Computer;
import com.excilys.mappers.ComputerDTOMapper;
import com.excilys.services.CompanyService;
import com.excilys.services.ComputerService;
import com.excilys.servlet.dto.ComputerDTO;

@Controller
@RequestMapping("/addComputer")
public class ComputerAddController {
	
	final Logger logger = LoggerFactory.getLogger(ComputerAddController.class);
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerDTOMapper mapper;
	
	private DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
	
    public ComputerAddController() {
        super();
        sdf.setLenient(true);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String showPage(Model model) {
		logger.trace("GET called on /addComputer : Showing computer add page, start up");
		model.addAttribute("show", false);
		model.addAttribute("computerDTO", new ComputerDTO());
		model.addAttribute("companies", companyService.getAllCompanies());
		logger.trace("GET called on /addComputer : Showing computer add page, response sent");
    	return "addComputer";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String addComputer(
    		/*@RequestParam String computerName,
    		@RequestParam(required = false) String introduced,
    		@RequestParam(required = false) String discontinued,
    		@RequestParam(required = false) Long companyId,*/
    		@ModelAttribute ComputerDTO dto,
    		Model model) {
    	
    	Computer computer = mapper.toComputer(dto);
    	/*
		if (StringValidator.isACorrectString(computerName)) {
			computer.setName(computerName);
		} else {
			model.addAttribute("show", true);
			model.addAttribute("showSuccess", false);
			model.addAttribute("message", "Problème avec le nom de l'ordinateur. Est-il vide?");
	    	return "addComputer";
		}
		if (DateValidator.isACorrectDate(introduced)) {
			try {
				computer.setIntroduced(LocalDateTime.ofInstant(sdf.parse(introduced).toInstant(), ZoneId.systemDefault()));
			} catch (ParseException e) {
				computer.setIntroduced(null);
			}			
		}
		if (DateValidator.isACorrectDate(discontinued)) {
			try {
				computer.setDiscontinued(LocalDateTime.ofInstant(sdf.parse(discontinued).toInstant(), ZoneId.systemDefault()));
			} catch (ParseException e) {
				computer.setDiscontinued(null);
			}
		}
		if (companyId != null) {
			Company company = companyService.getCompany(companyId);
			if (company != null) {
				computer.setCompany(company);
			}
		}
		*/
		computerService.createComputer(computer);
		model.addAttribute("companies", companyService.getAllCompanies());
		model.addAttribute("show", true);
		model.addAttribute("showSuccess", true);
		model.addAttribute("message", "Ordinateur ajouté. " + computer.toString());
		//model.addAttribute("computerDTO", new ComputerDTO());
    	return "addComputer";
    }

}
