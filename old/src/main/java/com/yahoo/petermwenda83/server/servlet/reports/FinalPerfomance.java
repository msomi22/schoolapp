/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.util.ArrayList;
import java.util.List;

import com.yahoo.petermwenda83.bean.exam.Perfomance;

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
