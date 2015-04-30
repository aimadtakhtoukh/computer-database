package com.excilys.webservices.service;

import java.util.List;
import java.util.stream.Collectors;

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

import com.excilys.service.services.ComputerService;
import com.excilys.webservices.dto.ComputerDTO;
import com.excilys.webservices.dto.converter.ComputerDTOConverter;

@Component
@Path("/computer")
public class ComputerRestController {
	
	@Autowired
	private ComputerService service;
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public List<ComputerDTO> getAllComputers() {
		return service.getAll().stream().map(ComputerDTOConverter::from).collect(Collectors.toList());
	}
	
	@GET
	@Path("/{param}")
	@Produces("application/json")
	public ComputerDTO getComputer(@PathParam("param") long id) {
		return ComputerDTOConverter.from(service.getComputer(id));
	}
	
	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Long addComputer(ComputerDTO computer) {
		return service.createComputer(ComputerDTOConverter.to(computer));
	}
	
	@PUT
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Long editComputer(ComputerDTO computer) {
		return service.updateComputer(ComputerDTOConverter.to(computer));
	}
	
	@DELETE
	@Path("/{param}")
	public void deleteComputer(@PathParam("param") long id) {
		service.deleteComputer(id);
	}

}
