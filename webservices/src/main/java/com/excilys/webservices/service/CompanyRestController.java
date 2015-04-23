package com.excilys.webservices.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.core.beans.Company;
import com.excilys.service.services.CompanyService;

@Component
@Path("company")
public class CompanyRestController {
	
	@Autowired
	private CompanyService service;
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public List<Company> getAllCompanies() {
		return service.getAllCompanies();
	}
	
	@GET
	@Path("/{param}")
	@Produces("application/json")
	public Company getCompanies(@PathParam("param") Long id) {
		return service.getCompany(id);
	}
	
	@DELETE
	@Path("/{param}")
	@Consumes("application/json")
	public void deleteCompany(@PathParam("param") Long id) {
		service.deleteCompanyAndRelatedComputers(id);
	}
	
}
