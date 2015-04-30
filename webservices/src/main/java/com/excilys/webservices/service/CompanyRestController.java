package com.excilys.webservices.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.service.services.CompanyService;
import com.excilys.webservices.dto.CompanyDTO;
import com.excilys.webservices.dto.converter.CompanyDTOConverter;

@Component
@Path("company")
public class CompanyRestController {
	
	@Autowired
	private CompanyService service;
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public List<CompanyDTO> getAllCompanies() {
		return service.getAllCompanies().stream().map(CompanyDTOConverter::from).collect(Collectors.toList());
	}
	
	@GET
	@Path("/{param}")
	@Produces("application/json")
	public CompanyDTO getCompanies(@PathParam("param") Long id) {
		return CompanyDTOConverter.from(service.getCompany(id));
	}
	
	@DELETE
	@Path("/{param}")
	@Consumes("application/json")
	public void deleteCompany(@PathParam("param") Long id) {
		service.deleteCompanyAndRelatedComputers(id);
	}
	
}
