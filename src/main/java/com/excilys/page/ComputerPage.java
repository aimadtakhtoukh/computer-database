package com.excilys.page;

import com.excilys.beans.Computer;
import com.excilys.dao.CRUDDAO;

public class ComputerPage extends PageImpl<Computer> implements Page<Computer> {
	
	public ComputerPage(CRUDDAO<Computer> dao) {
		super(dao);
	}
	
	public ComputerPage(CRUDDAO<Computer> list, int limit, int offset) {
		super(list, limit, offset);
	}
	
	public int getTotalCount() {
		if (hasASearchString()) {
			return super.getTotalCount();
		}
		return this.getDAO().getAll(0, super.getTotalCount(), null, false, this.getSearchString()).size();
	}
	
	public int getTotalPageNumber() {
		if (hasASearchString()) {
			return super.getTotalPageNumber();
		}
		return (this.getTotalCount() / this.getLimit()); 
	}
	
	private boolean hasASearchString() {
		if (this.getSearchString() == null) {
			return false;
		}
		return this.getSearchString().trim().equals("");
	}
}
