/**
 * 
 */
package com.yahoo.petermwenda83.util.performance.comparator;

import java.util.Comparator;

import com.yahoo.petermwenda83.server.servlet.reports.Performance2;

/**
 * @author peter
 *
 */
public class MeanComparator  implements Comparator<Performance2>{

	@Override
	public int compare(Performance2 o1, Performance2 o2) {
		if(o1.getTotalMean() > o2.getTotalMean()){
			return 1;
		}else if(o1.getTotalMean() < o2.getTotalMean()){ 
			return -1;
		}else{
			return 0;
		}
	}
	
}
