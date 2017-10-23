/**
 * 
 */
package com.yahoo.petermwenda83.util.performance.comparator;

import java.util.Comparator;

import com.yahoo.petermwenda83.server.servlet.reports.TBIDBean;

/**
 * @author peter
 *
 */
public class TBIDBeanComparator implements Comparator<TBIDBean>{

	
	@Override
	public int compare(TBIDBean o1, TBIDBean o2) {
		if(o1.getMean() > o2.getMean()){
			return 1;
		}else if(o1.getMean() < o2.getMean()){ 
			return -1;
		}else{
			return 0;
		}
	}

}
