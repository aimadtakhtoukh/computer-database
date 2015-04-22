package com.excilys.webservices.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.excilys.service.services.ComputerService;

@Path("/computer")
public class ComputerRestController {
	
	private ComputerService service = SpringApplicationContext.getBean(ComputerService.class);
	
	@GET
	@Path("/all")
	public Response getAllComputers() {
		return Response.ok(service.getAll()).build();
	}
	
	@GET
	@Path("/{param}")
	public Response getComputer(@PathParam("param") Long id) {
		return Response.ok(service.getComputer(id)).build();
	}

}
