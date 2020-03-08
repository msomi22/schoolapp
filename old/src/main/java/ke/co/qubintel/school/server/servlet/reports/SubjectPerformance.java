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
public class SubjectPerformance {
	
	private double total;
	private int entry;

	/**
	 * 
	 */
	public SubjectPerformance() {
		total = 0;
		entry = 0;
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
		return "SubjectPerformance [total=" + total + ", entry=" + entry + "]";
	}

}
