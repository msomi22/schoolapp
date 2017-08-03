package com.yahoo.petermwenda83.util.performance.comparator;

import java.util.Comparator;

import com.yahoo.petermwenda83.server.servlet.reports.SubjectAnalysis;
/**
 * 
 * @author peter
 *
 */
public class SubjectPointComparator implements Comparator<SubjectAnalysis>{

	@Override
	public int compare(SubjectAnalysis o1, SubjectAnalysis o2) {
		if((int)o1.getAverage() > o2.getAverage()){
			return 1;
		}else if(o1.getAverage() < o2.getAverage()){ 
			return -1;
		}else{
			return 0;
		}
	}

}
