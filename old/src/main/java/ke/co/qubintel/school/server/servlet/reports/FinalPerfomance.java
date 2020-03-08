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

import java.util.ArrayList;
import java.util.List;

import ke.co.qubintel.school.server.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public class FinalPerfomance {
	private int totalScore;
	private List<Perfomance> perfomanceList;
	
	

	/**
	 * 
	 */
	public FinalPerfomance() {
		totalScore = 0;
		perfomanceList = new ArrayList<>();
	}



	/**
	 * @return the totalScore
	 */
	public int getTotalScore() {
		return totalScore;
	}



	/**
	 * @param totalScore the totalScore to set
	 */
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}



	/**
	 * @return the perfomanceList
	 */
	public List<Perfomance> getPerfomanceList() {
		return perfomanceList;
	}



	/**
	 * @param perfomanceList the perfomanceList to set
	 */
	public void setPerfomanceList(List<Perfomance> perfomanceList) {
		this.perfomanceList = perfomanceList;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FinalPerfomance [totalScore=" + totalScore + ", perfomanceList=" + perfomanceList + "]";
	}
	
	

}
