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
package ke.co.qubintel.school.server.servlet.reports.test2;

public class MP{

	private String mean;
	private String pos;

	public MP(){
		mean = "";
		pos = "";
	}

	/**
	 * @return the mean
	 */
	public String getMean() {
		return mean;
	}

	/**
	 * @param mean the mean to set
	 */
	public void setMean(String mean) {
		this.mean = mean;
	}

	/**
	 * @return the pos
	 */
	public String getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(String pos) {
		this.pos = pos;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MP [mean=" + mean + ", pos=" + pos + "]";
	}

}
