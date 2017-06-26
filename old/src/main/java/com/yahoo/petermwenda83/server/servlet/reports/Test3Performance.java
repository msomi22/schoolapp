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
	
	private int total;
	private Map<String,Integer> perfomanceMap;

	/**
	 * 
	 */
	public Test3Performance() {
		total = 0;
		perfomanceMap = new HashMap<>(); 
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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
		return "Test3Performance [total=" + total + ", perfomanceMap=" + perfomanceMap + "]";
	}

	

	
}
