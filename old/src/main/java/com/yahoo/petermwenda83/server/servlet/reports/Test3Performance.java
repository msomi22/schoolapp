/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.util.HashMap;
import java.util.Map;

/**
 * @author peter
 *
 */
public class Test3Performance {
	
	private int totalMean;
	private int totalPoits;
	private Map<String,Integer> perfomanceMap;

	/**
	 * 
	 */
	public Test3Performance() {
		totalMean = 0;
		totalPoits = 0;
		perfomanceMap = new HashMap<>(); 
	}

	
	

	/**
	 * @return the totalMean
	 */
	public int getTotalMean() {
		return totalMean;
	}

	/**
	 * @param totalMean the totalMean to set
	 */
	public void setTotalMean(int totalMean) {
		this.totalMean = totalMean;
	}

	/**
	 * @return the totalPoits
	 */
	public int getTotalPoits() {
		return totalPoits;
	}

	/**
	 * @param totalPoits the totalPoits to set
	 */
	public void setTotalPoits(int totalPoits) {
		this.totalPoits = totalPoits;
	}

	/**
	 * @return the perfomanceMap
	 */
	public Map<String, Integer> getPerfomanceMap() {
		return perfomanceMap;
	}

	/**
	 * @param perfomanceMap the perfomanceMap to set
	 */
	public void setPerfomanceMap(Map<String, Integer> perfomanceMap) {
		this.perfomanceMap = perfomanceMap;
	}




	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Test3Performance [totalMean=" + totalMean + ", totalPoits=" + totalPoits + ", perfomanceMap="
				+ perfomanceMap + "]";
	}

	

	
}
