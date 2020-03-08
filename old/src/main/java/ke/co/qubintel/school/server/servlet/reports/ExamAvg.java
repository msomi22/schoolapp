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
public class ExamAvg {
	
	private int toatlAverage;
	private int totalPoint;

	/**
	 * 
	 */
	public ExamAvg() {
		toatlAverage = 0;
		totalPoint = 0;
	}

	/**
	 * @return the toatlAverage
	 */
	public int getToatlAverage() {
		return toatlAverage;
	}

	/**
	 * @param toatlAverage the toatlAverage to set
	 */
	public void setToatlAverage(int toatlAverage) {
		this.toatlAverage = toatlAverage;
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

	/*8
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExamAvg [toatlAverage=" + toatlAverage + ", totalPoint=" + totalPoint + "]";
	}

}
