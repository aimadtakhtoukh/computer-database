package com.excilys.page;

import com.excilys.beans.Computer;
import com.excilys.dao.CRUDDAO;

public class ComputerPage extends PageImpl<Computer> implements Page<Computer> {
	
	public ComputerPage(CRUDDAO<Computer> dao) {
		super(dao);
	}
	
	public ComputerPage(CRUDDAO<Computer> list, int limit, int offset,
			String order) {
		super(list, limit, offset, order);
	}
	
	public int getTotalCount() {
		return super.getTotalCount();
	}
	
	public int getTotalPageNumber() {
		return super.getTotalPageNumber();
	}
	
	public void setSearchString(String searchString) {
		super.setSearchString(searchString);
	}
}
