package com.excilys.console.rest;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.excilys.core.beans.Computer;

public class ComputerRestClient {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		List<Computer> c = 
				client.target("http://localhost:8080/webservices/rest/computer/all")
				.request("application/json")
				.get(List.class);
		System.out.println(c);
	}

}
