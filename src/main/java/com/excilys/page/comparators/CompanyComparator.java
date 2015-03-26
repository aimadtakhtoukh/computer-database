package com.excilys.page.comparators;

import java.util.Comparator;

import com.excilys.beans.Company;

public enum CompanyComparator implements Comparator<Company> {
	COMPARE_BY_ID {
		@Override
		public int compare(Company o1, Company o2) {
			if (o1 == null) {
				if (o2 == null) {
					return 0;
				} else {
					return 1;
				}
			}
			if (o2 == null) {
				return -1;
			}
			if (o1.getId() == null) {
				if (o2.getId() == null) {
					return 0;
				} else {
					return 1;
				}
			}
			if (o2.getId() == null) {
				return -1;
			}
			return (int) (o1.getId() - o2.getId());
		}
	},
	COMPARE_BY_NAME {
		@Override
		public int compare(Company o1, Company o2) {
			if (o1 == null) {
				if (o2 == null) {
					return 0;
				} else {
					return -1;
				}
			}
			if (o2 == null) {
				return 1;
			}
			if (o1.getName() == null) {
				if (o2.getName() == null) {
					return 0;
				} else {
					return -1;
				}
			}
			if (o2.getName() == null) {
				return 1;
			}
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
	};

	@Override
	public int compare(Company o1, Company o2) {
		return 0;
	}
}
