package com.yahoo.petermwenda83.util.performance.comparator;

import java.util.Comparator;

import com.yahoo.petermwenda83.server.servlet.reports.Performance1;
/**
 * 
 * @author peter
 *
 */
public class SubjectPointComparator implements Comparator<Performance1>{

	@Override
	public int compare(Performance1 o1, Performance1 o2) {
		if(o1.getTotalPoint() > o2.getTotalPoint()){
			return 1;
		}else if(o1.getTotalPoint() < o2.getTotalPoint()){ 
			return -1;
		}else{
			return 0;
		}
	}

}
