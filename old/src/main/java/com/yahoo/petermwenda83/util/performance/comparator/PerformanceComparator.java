package com.yahoo.petermwenda83.util.performance.comparator;

import java.util.Comparator;

import com.yahoo.petermwenda83.bean.exam.Perfomance;

public class PerformanceComparator implements Comparator<Perfomance> {

	@Override
	public int compare(Perfomance o1, Perfomance o2) {
		if(o1.getScore() > o2.getScore()){
			return 1;
		}else if(o1.getScore() < o2.getScore()){ 
			return -1;
		}else{
			return 0;
		}
	}

}
