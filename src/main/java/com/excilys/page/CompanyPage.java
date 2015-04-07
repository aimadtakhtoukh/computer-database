package com.excilys.page;

import com.excilys.beans.Company;
import com.excilys.dao.CRUDDAO;

public class CompanyPage extends PageImpl<Company> implements Page<Company> {

	public CompanyPage(CRUDDAO<Company> dao) {
		super(dao);
	}
	
	public CompanyPage(CRUDDAO<Company> dao, int limit, int offset, String order) {
		super(dao, limit, offset, order);
	}

}
