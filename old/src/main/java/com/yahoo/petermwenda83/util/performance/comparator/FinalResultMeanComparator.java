package com.yahoo.petermwenda83.util.performance.comparator;

import java.util.Comparator;

import com.yahoo.petermwenda83.server.servlet.reports.FinaResult;

public class FinalResultMeanComparator implements Comparator<FinaResult>{

	@Override
	public int compare(FinaResult o1, FinaResult o2) {
		if(o1.getAverage() > o2.getAverage()){
			return 1;
		}else if(o1.getAverage() < o2.getAverage()){ 
			return -1;
		}else{
			return 0;
		}
	}
}
