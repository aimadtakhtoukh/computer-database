package com.excilys.page.comparators;

import java.util.Comparator;

import com.excilys.beans.Computer;

public enum ComputerComparator implements Comparator<Computer> {
	COMPARE_BY_ID {
		public int compare(Computer o1, Computer o2) {
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
		public int compare(Computer o1, Computer o2) {
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
			if (o1.getName() == null) {
				if (o2.getName() == null) {
					return 0;
				} else {
					return 1;
				}
			}
			if (o2.getName() == null) {
				return -1;
			}
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
	},
	COMPARE_BY_INTRODUCED_DATE {
		@Override
		public int compare(Computer o1, Computer o2) {
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
			if (o1.getIntroduced() == null) {
				if (o2.getIntroduced() == null) {
					return 0;
				} else {
					return 1;
				}
			}
			if (o2.getIntroduced() == null) {
				return -1;
			}
			return o1.getIntroduced().compareTo(o2.getIntroduced());
		}
	},
	COMPARE_BY_DISCONTINUED_DATE {
		@Override
		public int compare(Computer o1, Computer o2) {
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
			if (o1.getDiscontinued() == null) {
				if (o2.getDiscontinued() == null) {
					return 0;
				} else {
					return 1;
				}
			}
			if (o2.getDiscontinued() == null) {
				return -1;
			}
			return o1.getDiscontinued().compareTo(o2.getDiscontinued());
		}
	},
	COMPARE_BY_COMPANY {
		@Override
		public int compare(Computer o1, Computer o2) {
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
			if (o1.getCompany() == null) {
				if (o2.getCompany() == null) {
					return 0;
				} else {
					return 1;
				}
			}
			if (o2.getCompany() == null) {
				return -1;
			}
			return CompanyComparator.COMPARE_BY_NAME.compare(o1.getCompany(), o2.getCompany());
		}
	}
	;

	@Override
	public int compare(Computer o1, Computer o2) {
		return 0;
	}
	

}
