/**
 * 
 */
package com.yahoo.petermwenda83.util.performance.comparator;

import java.util.Comparator;

import com.yahoo.petermwenda83.server.servlet.reports.FinalPerfomance;

/**
 * @author peter
 *
 */
public class FinalPerfomancescomparator implements Comparator<FinalPerfomance> {


	@Override
	public int compare(FinalPerfomance o1, FinalPerfomance o2) {
		if(o1.getTotalScore() > o2.getTotalScore()){
			return 1;
		}else if(o1.getTotalScore() < o2.getTotalScore()){ 
			return -1;
		}else{
			return 0;
		}
	}

}
