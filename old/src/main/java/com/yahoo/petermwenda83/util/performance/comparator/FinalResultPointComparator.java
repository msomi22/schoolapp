package com.yahoo.petermwenda83.util.performance.comparator;

import java.util.Comparator;

import com.yahoo.petermwenda83.server.servlet.reports.FinaResult;

public class FinalResultPointComparator implements Comparator<FinaResult>{

	@Override
	public int compare(FinaResult o1, FinaResult o2) {
		if(o1.getPoint() > o2.getPoint()){
			return 1;
		}else if(o1.getPoint() < o2.getPoint()){ 
			return -1;
		}else{
			return 0;
		}
	}
}
