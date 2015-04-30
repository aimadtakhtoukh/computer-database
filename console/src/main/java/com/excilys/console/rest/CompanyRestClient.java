package com.excilys.console.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.excilys.core.beans.Company;

/**
 * A REST client used by the CLI, for Companies.
 * @author excilys
 *
 */
@Component
public class CompanyRestClient {
	private class CompanyList extends ArrayList<Company> implements List<Company> {
		private static final long serialVersionUID = -2251483515551659128L;		
	}
	
	/**
	 * Return the company with the same id than the parameter.
	 * @param id
	 * @return
	 */
	public Company getCompany(long id) {
		Client client = ClientBuilder.newClient();
		return client.target("http://localhost:8080/webservices/rest/company/" + id)
				.request("application/json")
				.get(Company.class);
	}
	
	/**
	 * Return all companies in the database.
	 * @return
	 */
	public List<Company> getComputers() {
		Client client = ClientBuilder.newClient();
		return client.target("http://localhost:8080/webservices/rest/company/all")
				.request("application/json")
				.get(CompanyList.class);
	}
	
	/**
	 * Deletes the company with the same id than the parameter.
	 * @param id
	 */	
	public void deleteCompany(long id) {
		Client client = ClientBuilder.newClient();
		client.target("http://localhost:8080/webservices/rest/company/" + id)
			.request(MediaType.APPLICATION_JSON).delete();
	}
}
