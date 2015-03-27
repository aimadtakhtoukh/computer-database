package com.excilys.page;

import java.util.List;
import java.util.stream.Collectors;

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

	List<Computer> resultList = null;
	
	public List<Computer> getPageElements() {
		return super.getDAO().
	}
	
	public int getTotalCount() {
		if (resultList == null) {
			return super.getTotalCount();
		}
		return resultList.size();
	}
	
	public int getTotalPageNumber() {
		if (resultList == null) {
			return super.getTotalPageNumber();
		}
		return (resultList.size() / getLimit());
	}
	
	public void setSearchString(String searchString) {
		super.setSearchString(searchString);
		setUpSearchResultList();
	}
	
	private void setUpSearchResultList() {
		if (this.getSearchString() == null) {
			resultList = null;
			return;
		}
		if (this.getSearchString().isEmpty()) {
			resultList = null;
			return;
		}
		List<Computer> result = super.getAllElements().stream()
				.filter(
						computer -> 
						computer.getName().contains(this.getSearchString())
						|| 
						companyContainsSearchString(computer, this.getSearchString())
				)
				.collect(Collectors.toList());
		resultList = result;
		if (this.getOffset() > resultList.size()) {
			this.setOffset(0);
		}
	}

	
	private boolean companyContainsSearchString(Computer computer, String searchString) {
		if (computer.getCompany() == null) {
			return false;
		}
		if (computer.getCompany().getName() == null) {
			return false;
		}
		return computer.getCompany().getName().contains(searchString);
	}

	@Override
	public void setOrder(String column, boolean ascendent) {
		// TODO Auto-generated method stub
		
	}
}
