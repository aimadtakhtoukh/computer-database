package com.excilys.persistence.page;

import com.excilys.core.beans.Company;
import com.excilys.persistence.dao.CRUDDAO;

public class CompanyPage extends PageImpl<Company> implements Page<Company> {

	public CompanyPage(CRUDDAO<Company> dao) {
		super(dao);
	}
	
	public CompanyPage(CRUDDAO<Company> dao, int limit, int offset) {
		super(dao, limit, offset);
	}

}
