/**
 * 
 */
package com.yahoo.petermwenda83.util.performance.comparator;

import java.util.Comparator;

import com.yahoo.petermwenda83.server.servlet.reports.Test3Object;

/**
 * @author peter
 *
 */
public class Test3ObjectComparator implements Comparator<Test3Object>{

	@Override
	public int compare(Test3Object o1, Test3Object o2) {
		if(o1.getTotalScore() > o2.getTotalScore()){
			return 1;
		}else if(o1.getTotalScore() < o2.getTotalScore()){ 
			return -1;
		}else{
			return 0;
		}
	}

}
