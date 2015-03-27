package com.excilys.page;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.beans.Computer;

public class ComputerPage extends PageImpl<Computer> implements Page<Computer> {
	
	List<Computer> resultList = null;

	public ComputerPage(List<Computer> list) {
		super(list);
		setUpSearchResultList();
	}

	public ComputerPage(List<Computer> list, int limit, int offset) {
		super(list, limit, offset);
		setUpSearchResultList();
	}
	
	public List<Computer> getPageElements() {
		if (resultList == null) {
			return super.getPageElements();
		}
		return resultList.subList(Math.max(0, this.getOffset()), 
				Math.min(this.getOffset() + this.getLimit(), resultList.size()));
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
}
