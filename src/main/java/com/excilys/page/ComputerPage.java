package com.excilys.page;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.beans.Computer;

public class ComputerPage extends PageImpl<Computer> implements Page<Computer> {

	public ComputerPage(List<Computer> list) {
		super(list);
	}

	public ComputerPage(List<Computer> list, int limit, int offset) {
		super(list, limit, offset);
	}
	
	public List<Computer> getPageElements() {
		if (this.getSearchString() == null) {
			return super.getPageElements();
		}
		if (this.getSearchString().isEmpty()) {
			return super.getPageElements();
		}
		List<Computer> result = super.getAllElements().stream()
				.filter(
						computer -> 
						computer.getName().contains(this.getSearchString())
						|| computer.getCompany().getName().contains(this.getSearchString())
				)
				.collect(Collectors.toList());
		return result.subList(Math.max(0, this.getOffset()), 
						Math.min(this.getOffset() + this.getLimit(), result.size()));
	}

}
