package com.excilys.servlet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.beans.Computer;
import com.excilys.mappers.ComputerDTOMapper;
import com.excilys.services.CompanyService;
import com.excilys.services.ComputerService;
import com.excilys.servlet.dto.ComputerDTO;

@Controller
@RequestMapping("/editComputer")
public class ComputerEditController {	

	final Logger logger = LoggerFactory.getLogger(ComputerEditController.class);

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerDTOMapper mapper;
	
	private DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
	
	public ComputerEditController() {
        super();
        sdf.setLenient(true);
    }
	
	@RequestMapping(method = RequestMethod.GET)
	public String showEditedComputerPage(@RequestParam Long id, Model model) {
		logger.trace("GET called on /editComputer : Showing computer edit page, start up");
		if (id == null) {
			logger.trace("id isn't a number.");
			return "redirect:/dashboard";
		}
		Computer computer = computerService.getComputer(id);
		/*
		model.addAttribute("computerId", computer.getId());
		model.addAttribute("computerName", computer.getName());
		if (computer.getIntroduced() != null) {
			model.addAttribute("computerIntroduced", getFormattedTime(computer.getIntroduced().toLocalDate()));
		}
		if (computer.getDiscontinued() != null) { 
			model.addAttribute("computerDiscontinued", getFormattedTime(computer.getDiscontinued().toLocalDate()));
		}
		if (computer.getCompany() != null) {
			model.addAttribute("companySelectedId", computer.getCompany().getId());
		} else {
			model.addAttribute("companySelectedId", 0);
		}
		*/
		model.addAttribute("computerDTO", mapper.toComputerDTO(computer));
		model.addAttribute("companies", companyService.getAllCompanies());
		logger.trace("GET called on /editComputer : Showing computer edit page,  response sent");		
		return "editComputer";		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String editComputer(
			@ModelAttribute ComputerDTO dto,
			Model model) {
		logger.trace("POST called on /editComputer : Editing computer, start up");
		Computer computer = mapper.toComputer(dto);
		/*
		if (computerId != null) {
			computer.setId(computerId);
		} else {
			logger.trace("id isn't a number");
			return "redirect:/dashboard";
		}
		if (StringValidator.isACorrectString(computerName)) {
			computer.setName(computerName);
		} else {
			logger.trace("name isn't a correct name");
			return "redirect:/dashboard";
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
		computerService.updateComputer(computer);
		logger.trace("POST called on /editComputer : Editing computer, response sent");		
		return "redirect:/dashboard";
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
