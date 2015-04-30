package com.excilys.webapp.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.core.beans.Computer;
import com.excilys.service.services.ComputerService;

/**
 * REST controller for computers.
 * @author excilys
 *
 */
@RestController
@RequestMapping("/rest")
public class ComputerRestController {
	
	@Autowired
	private ComputerService service;

	/**
	 * Returns a computer as a JSON.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public Computer getComputer(@PathVariable long id) {
		return service.getComputer(id);
	}
	
	/**
	 * Returns all computers in a JSON array.
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Computer> getComputers() {
		return service.getAll();
	}
	
}
