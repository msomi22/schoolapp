/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

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
