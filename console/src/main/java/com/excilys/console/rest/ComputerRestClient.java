package com.excilys.console.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.excilys.core.beans.Computer;

@Component
public class ComputerRestClient {
	
	private class ComputerList extends ArrayList<Computer> implements List<Computer> {
		private static final long serialVersionUID = -2251483515551659128L;		
	}
	
	public Computer getComputer(long id) {
		Client client = ClientBuilder.newClient();
		return client.target("http://localhost:8080/webservices/rest/computer/" + id)
				.request("application/json")
				.get(Computer.class);
	}
	
	public List<Computer> getComputers() {
		Client client = ClientBuilder.newClient();
		return client.target("http://localhost:8080/webservices/rest/computer/all")
				.request("application/json")
				.get(ComputerList.class);
	}
	
	public long addComputer(Computer c) {
		Client client = ClientBuilder.newClient();
		return client
				.target("http://localhost:8080/webservices/rest/computer/")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(c), Long.class);
	}
	
	public long editComputer(Computer c) {
		Client client = ClientBuilder.newClient();
		return client.target("http://localhost:8080/webservices/rest/computer/")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.json(c), Long.class);		
	}
	
	public void deleteComputer(long id) {
		Client client = ClientBuilder.newClient();
		client.target("http://localhost:8080/webservices/rest/computer/" + id).request(MediaType.APPLICATION_JSON).delete();
	}
}
