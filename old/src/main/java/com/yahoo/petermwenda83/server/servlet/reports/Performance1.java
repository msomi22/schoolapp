/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

/** com.yahoo.petermwenda83.server.servlet.reports
 * @author peter
 *
 */
public class Performance1{
	
	private String subjectId;
	private int totalMean;
	private int totalPoint;
	
	public Performance1(){
		subjectId = "";
		totalMean = 0;
		totalPoint = 0;
	}

	/**
	 * @return the subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
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
	 * @return the totalPoint
	 */
	public int getTotalPoint() {
		return totalPoint;
	}

	/**
	 * @param totalPoint the totalPoint to set
	 */
	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Performance1 [subjectId=" + subjectId + ", totalMean=" + totalMean + ", totalPoint=" + totalPoint + "]";
	}
	
	
}
