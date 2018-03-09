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

import ke.co.qubintel.school.server.bean.subject.Subject;

/**
 * @author peter
 *
 */
public class FinaResult {
	
	private String subjectId;
	private int average;
	private int point;

	/**
	 * 
	 */
	public FinaResult() {
		subjectId = "";
		average = 0;
		point = 0;
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
	 * @return the average
	 */
	public int getAverage() {
		return average;
	}

	/**
	 * @param average the average to set
	 */
	public void setAverage(int average) {
		this.average = average;
	}

	/**
	 * @return the point
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(int point) {
		this.point = point;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FinaResult [subjectId=" + subjectId + ", average=" + average + ", point=" + point + "]";
	}

}
