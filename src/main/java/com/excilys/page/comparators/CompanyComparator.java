package com.excilys.page.comparators;

import java.util.Comparator;

import com.excilys.beans.Company;

public class CompanyComparator {
	
	public static Comparator<Company> comparebyId = new Comparator<Company>() {
		@Override
		public int compare(Company o1, Company o2) {
			return (int) (o1.getId() - o2.getId());
		}
	};
	
	public static Comparator<Company> compareByName = new Comparator<Company>() {
		@Override
		public int compare(Company o1, Company o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};

}
