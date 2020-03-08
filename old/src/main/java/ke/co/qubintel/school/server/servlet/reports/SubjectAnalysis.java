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

/**
 * @author peter
 *
 */
public class SubjectAnalysis {
	
	private String subjectId;
	private double total;
	private double average;
	private int entry;
	
	/**
	 * 
	 */
	public SubjectAnalysis() {
		subjectId = "";
		total = 0;
		average = 0;
		entry = 0;
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
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * @return the average
	 */
	public double getAverage() {
		return average;
	}

	/**
	 * @param average the average to set
	 */
	public void setAverage(double average) {
		this.average = average;
	}

	/**
	 * @return the entry
	 */
	public int getEntry() {
		return entry;
	}

	/**
	 * @param entry the entry to set
	 */
	public void setEntry(int entry) {
		this.entry = entry;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubjectAnalysis [subjectId=" + subjectId + ", total=" + total + ", average=" + average + ", entry="
				+ entry + "]";
	}

}
