/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

/**
 * @author peter
 *
 */
public class PerformanceP123 {
	
	private int totalMean;
	private int totalPoints;
	private String studentId;

	/**
	 * 
	 */
	public PerformanceP123() {
		totalMean = 0;
		totalPoints = 0;
		studentId = "";
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

	
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	@Override
	public String toString() {
		return "PerformanceP123 [totalMean=" + totalMean + ", totalPoints=" + totalPoints + ", studentId=" + studentId
				+ "]";
	}
	
	

}
