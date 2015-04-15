package com.excilys.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.beans.Computer;
import com.excilys.controller.dto.ComputerDTO;
import com.excilys.mappers.ComputerDTOMapper;
import com.excilys.page.ComputerPage;
import com.excilys.services.CompanyService;
import com.excilys.services.ComputerService;
import com.excilys.validator.StringValidation;

@Controller
public class ComputerController {
	private final Logger logger = LoggerFactory.getLogger(ComputerController.class);
	
	private static final int PAGE_NUMBER = 5;
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerDTOMapper mapper;
	@Autowired
	private StringValidation stringValidator;
	
	private Map<String, String> orderByStrings = new HashMap<>();
	
    public ComputerController() {
        orderByStrings.put("id", "computer.id");
        orderByStrings.put("name", "computer.name");
        orderByStrings.put("introduced", "computer.introduced");
        orderByStrings.put("discontinued", "computer.discontinued");
        orderByStrings.put("company", "company.name");
    }
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String showDashboard(@RequestParam(required = false) Integer resultsPerPage, 
								@RequestParam(required = false) Integer page, 
								@RequestParam(required = false) String search, 
								@RequestParam(required = false) String orderBy,
								@RequestParam(required = false) Boolean asc,
								Model model) {
		logger.trace("GET called on /dashboard : Showing dashboard, start up");
		
		ComputerPage computerPage = computerService.getComputerPage();
		int currentResultsPerPage = computerPage.getLimit();
		if (resultsPerPage != null) {
			currentResultsPerPage = verifyCurrentResultsPerPageParameter(resultsPerPage);
			computerPage.setLimit(currentResultsPerPage);
		}
		if (page != null) {
			int currentPage = verifyCurrentPageParameter(page);
			computerPage.goToPage(currentPage);
		}
		if (search != null) {
			computerPage.setSearchString(search);
		}
		boolean ascendentOrderBy = computerPage.isAscendent();
		if (asc != null) {
			ascendentOrderBy = asc;
		}
		if (orderBy != null) {
			if (orderByStrings.get(orderBy) != null) {
				computerPage.setOrder(orderByStrings.get(orderBy), ascendentOrderBy);
			}
		}
		model.addAttribute("computerCount", computerPage.getTotalCount());
		model.addAttribute("items", computerPage.getPageElements().stream().map(c -> mapper.toComputerDTO(c)).collect(Collectors.toList()));
		// Navigation attributes
		int current = computerPage.getCurrentPageNumber();
		model.addAttribute("paginationStart", Math.max(1, current - PAGE_NUMBER));
		model.addAttribute("paginationFinish", Math.min(current + PAGE_NUMBER, computerPage.getTotalPageNumber() + 1));
		model.addAttribute("currentPageNumber", current + 1);
		model.addAttribute("totalPageNumber", computerPage.getTotalPageNumber() + 1);
		model.addAttribute("resultsPerPage", currentResultsPerPage);
		// Variables de recherche
		model.addAttribute("search", search);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("asc", asc);
		logger.trace("GET called on /dashboard : Showing dashboard, response sent");
		return "dashboard";
	}

    @RequestMapping(value = "/addComputer", method = RequestMethod.GET)
    public String showPage(Model model) {
		logger.trace("GET called on /addComputer : Showing computer add page, start up");
		model.addAttribute("show", false);
		model.addAttribute("computerDTO", new ComputerDTO());
		model.addAttribute("companies", companyService.getAllCompanies());
		logger.trace("GET called on /addComputer : Showing computer add page, response sent");
    	return "addComputer";
    }
    
    @RequestMapping(value = "/addComputer", method = RequestMethod.POST)
    public String addComputer(
    		@Valid @ModelAttribute ComputerDTO dto,
    		BindingResult bindingResult,
    		Model model) {
    	
    	if (!bindingResult.hasErrors()) {
    		Computer computer = mapper.toComputer(dto);
    		computerService.createComputer(computer);
    		model.addAttribute("show", true);
    		model.addAttribute("showSuccess", true);
    		model.addAttribute("message", "Ordinateur ajouté. " + computer.toString());
    		model.addAttribute("companies", companyService.getAllCompanies());
    		model.addAttribute("computerDTO", new ComputerDTO());
        	return "addComputer";
    	}
    	return "500";
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
    }
    
    @RequestMapping(value = "/editComputer", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/editComputer", method = RequestMethod.POST)
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
	
	@RequestMapping(value="/deleteComputer", method = RequestMethod.POST)
	public String deleteComputers(@RequestParam String selection, Model model) {
		logger.trace("POST called on /dashboard : deleting computers, start up");
		if (!stringValidator.isACorrectString(selection)) {
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
	
	//OUTILS
	
	private int verifyCurrentPageParameter(Integer param) {
		if (param < 1) {return 0;}
		return param - 1;
	}
	
	private int verifyCurrentResultsPerPageParameter(Integer param) {
		if (param < 1) {return 10;}
		return param;
	}

}
