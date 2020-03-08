/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.servlet.reports;

import java.util.HashMap;
import java.util.Map;

/**
 * @author peter
 *
 */
public class Performance3 {
	
	private int totalMean;
	private int totalPoints;
	private Map<String,Integer> perfomanceMap;
	private Map<String,Integer> paper1Map;
	private Map<String,Integer> paper2Map;
	private Map<String,Integer> paper3Map;

	/**
	 * 
	 */
	public Performance3() {
		totalMean = 0;
		totalPoints = 0;
		perfomanceMap = new HashMap<>(); 
		paper1Map = new HashMap<>(); 
		paper2Map = new HashMap<>(); 
		paper3Map = new HashMap<>(); 
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
	 * @return the totalPoints
	 */
	public int getTotalPoints() {
		return totalPoints;
	}

	/**
	 * @param totalPoints the totalPoints to set
	 */
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
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
	 * @return the paper1Map
	 */
	public Map<String, Integer> getPaper1Map() {
		return paper1Map;
	}

	/**
	 * @param paper1Map the paper1Map to set
	 */
	public void setPaper1Map(Map<String, Integer> paper1Map) {
		this.paper1Map = paper1Map;
	}

	/**
	 * @return the paper2Map
	 */
	public Map<String, Integer> getPaper2Map() {
		return paper2Map;
	}

	/**
	 * @param paper2Map the paper2Map to set
	 */
	public void setPaper2Map(Map<String, Integer> paper2Map) {
		this.paper2Map = paper2Map;
	}

	/**
	 * @return the paper3Map
	 */
	public Map<String, Integer> getPaper3Map() {
		return paper3Map;
	}

	/**
	 * @param paper3Map the paper3Map to set
	 */
	public void setPaper3Map(Map<String, Integer> paper3Map) {
		this.paper3Map = paper3Map;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Performance3 [totalMean=" + totalMean + ", totalPoints=" + totalPoints + ", perfomanceMap="
				+ perfomanceMap + ", paper1Map=" + paper1Map + ", paper2Map=" + paper2Map + ", paper3Map=" + paper3Map
				+ "]";
	}

	
	
}
