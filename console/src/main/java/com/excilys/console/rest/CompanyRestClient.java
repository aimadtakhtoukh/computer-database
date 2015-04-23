package com.excilys.console.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.excilys.core.beans.Company;

@Component
public class CompanyRestClient {
	private class CompanyList extends ArrayList<Company> implements List<Company> {
		private static final long serialVersionUID = -2251483515551659128L;		
	}
	
	public Company getCompany(long id) {
		Client client = ClientBuilder.newClient();
		return client.target("http://localhost:8080/webservices/rest/company/" + id)
				.request("application/json")
				.get(Company.class);
	}
	
	public List<Company> getComputers() {
		Client client = ClientBuilder.newClient();
		return client.target("http://localhost:8080/webservices/rest/company/all")
				.request("application/json")
				.get(CompanyList.class);
	}
	
	public void deleteCompany(long id) {
		Client client = ClientBuilder.newClient();
		client.target("http://localhost:8080/webservices/rest/company/" + id)
			.request(MediaType.APPLICATION_JSON).delete();
	}
}
