package com.excilys.servlet;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.mappers.ComputerDTOMapper;
import com.excilys.page.ComputerPage;
import com.excilys.services.ComputerService;

@Controller
@RequestMapping("/dashboard")
public class ComputerListingController {
	final Logger logger = LoggerFactory.getLogger(ComputerListingController.class);
	
	private static final int PAGE_NUMBER = 5;
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerDTOMapper mapper;
	
	private Map<String, String> orderByStrings = new HashMap<>();
	
    public ComputerListingController() {
        super();
        orderByStrings.put("id", "computer.id");
        orderByStrings.put("name", "computer.name");
        orderByStrings.put("introduced", "computer.introduced");
        orderByStrings.put("discontinued", "computer.discontinued");
        orderByStrings.put("company", "company.name");
    }
	
	@RequestMapping(method = RequestMethod.GET)
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
	
	private int verifyCurrentPageParameter(Integer param) {
		if (param < 1) {return 0;}
		return param - 1;
	}
	
	private int verifyCurrentResultsPerPageParameter(Integer param) {
		if (param < 1) {return 10;}
		return param;
	}

}
