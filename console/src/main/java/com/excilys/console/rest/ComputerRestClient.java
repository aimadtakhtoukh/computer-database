package com.excilys.console.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.excilys.core.beans.Computer;

/**
 * A REST client used by the CLI, for Computers.
 * @author excilys
 *
 */
@Component
public class ComputerRestClient {
	
	private class ComputerList extends ArrayList<Computer> implements List<Computer> {
		private static final long serialVersionUID = -2251483515551659128L;		
	}
	
	/**
	 * Return the computer with the same id than the parameter.
	 * @param id
	 * @return
	 */
	public Computer getComputer(long id) {
		Client client = ClientBuilder.newClient();
		return client.target("http://localhost:8080/webservices/rest/computer/" + id)
				.request("application/json")
				.get(Computer.class);
	}
	
	/**
	 * Return all computers in the database.
	 * @return
	 */
	public List<Computer> getComputers() {
		Client client = ClientBuilder.newClient();
		return client.target("http://localhost:8080/webservices/rest/computer/all")
				.request("application/json")
				.get(ComputerList.class);
	}
	
	/**
	 * Adds the computer in the database.
	 * @param c
	 * @return
	 */
	public long addComputer(Computer c) {
		Client client = ClientBuilder.newClient();
		return client
				.target("http://localhost:8080/webservices/rest/computer/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(c), Long.class);
	}
	
	/**
	 * Edits the computer in the database with the same id than the computer parameter.
	 * @param c
	 * @return
	 */
	public long editComputer(Computer c) {
		Client client = ClientBuilder.newClient();
		return client.target("http://localhost:8080/webservices/rest/computer/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.json(c), Long.class);		
	}
	
	/**
	 * Deletes the computer with the same id than the parameter.
	 * @param id
	 */	
	public void deleteComputer(long id) {
		Client client = ClientBuilder.newClient();
		client.target("http://localhost:8080/webservices/rest/computer/" + id).request(MediaType.APPLICATION_JSON).delete();
	}
}
