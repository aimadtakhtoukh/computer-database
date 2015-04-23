package com.excilys.webservices.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.core.beans.Computer;
import com.excilys.service.services.ComputerService;

@Component
@Path("/computer")
public class ComputerRestController {
	
	@Autowired
	private ComputerService service;
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public List<Computer> getAllComputers() {
		return service.getAll();
	}
	
	@GET
	@Path("/{param}")
	@Produces("application/json")
	public Computer getComputer(@PathParam("param") long id) {
		return service.getComputer(id);
	}
	
	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Long addComputer(Computer computer) {
		return service.createComputer(computer);
	}
	
	@PUT
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Long editComputer(Computer computer) {
		return service.updateComputer(computer);
	}
	
	@DELETE
	@Path("/{param}")
	public void deleteComputer(@PathParam("param") long id) {
		service.deleteComputer(id);
	}

}
