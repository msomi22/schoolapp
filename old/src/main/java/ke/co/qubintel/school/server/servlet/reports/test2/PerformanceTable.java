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

/**
 * @author peter
 *
 */
public class PerformanceTable {

	private TermOneObj termOneObj;
	private TermTwoObj termTwoObj;
	private TermThreeObj termThreeObj;

	/**
	 * 
	 */
	public PerformanceTable() {
		termOneObj = new TermOneObj();
		termTwoObj = new TermTwoObj();
		termThreeObj = new TermThreeObj();
	}

	/**
	 * @return the termOneObj
	 */
	public TermOneObj getTermOneObj() {
		return termOneObj;
	}

	/**
	 * @param termOneObj the termOneObj to set
	 */
	public void setTermOneObj(TermOneObj termOneObj) {
		this.termOneObj = termOneObj;
	}

	/**
	 * @return the termTwoObj
	 */
	public TermTwoObj getTermTwoObj() {
		return termTwoObj;
	}

	/**
	 * @param termTwoObj the termTwoObj to set
	 */
	public void setTermTwoObj(TermTwoObj termTwoObj) {
		this.termTwoObj = termTwoObj;
	}

	/**
	 * @return the termThreeObj
	 */
	public TermThreeObj getTermThreeObj() {
		return termThreeObj;
	}

	/**
	 * @param termThreeObj the termThreeObj to set
	 */
	public void setTermThreeObj(TermThreeObj termThreeObj) {
		this.termThreeObj = termThreeObj;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PerformanceTable [termOneObj=" + termOneObj + ", termTwoObj=" + termTwoObj + ", termThreeObj="
				+ termThreeObj + "]";
	}

}
