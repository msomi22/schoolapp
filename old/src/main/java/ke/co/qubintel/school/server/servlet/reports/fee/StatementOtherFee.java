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
package ke.co.qubintel.school.server.servlet.reports.fee;

import java.sql.Timestamp;

/**
 * @author peter
 *
 */
public class StatementOtherFee {
	
	private int otherAmount;
	private String otherAmountTerm;
	private String otherAmountYear;
	private String otherAmountDescription;
	private Timestamp otherAmountDateAllocated;

	/**
	 * 
	 */
	public StatementOtherFee() {
		otherAmount = 0;
		otherAmountTerm = "";
		otherAmountYear = "";
		otherAmountDescription = "";
		otherAmountDateAllocated = null;
	}

	public int getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(int otherAmount) {
		this.otherAmount = otherAmount;
	}

	public String getOtherAmountTerm() {
		return otherAmountTerm;
	}

	public void setOtherAmountTerm(String otherAmountTerm) {
		this.otherAmountTerm = otherAmountTerm;
	}

	public String getOtherAmountYear() {
		return otherAmountYear;
	}

	public void setOtherAmountYear(String otherAmountYear) {
		this.otherAmountYear = otherAmountYear;
	}

	public String getOtherAmountDescription() {
		return otherAmountDescription;
	}

	public void setOtherAmountDescription(String otherAmountDescription) {
		this.otherAmountDescription = otherAmountDescription;
	}

	public Timestamp getOtherAmountDateAllocated() {
		return otherAmountDateAllocated;
	}

	public void setOtherAmountDateAllocated(Timestamp otherAmountDateAllocated) {
		this.otherAmountDateAllocated = otherAmountDateAllocated;
	}

	@Override
	public String toString() {
		return "StatementOtherFee [otherAmount=" + otherAmount + ", otherAmountTerm=" + otherAmountTerm
				+ ", otherAmountYear=" + otherAmountYear + ", otherAmountDescription=" + otherAmountDescription
				+ ", otherAmountDateAllocated=" + otherAmountDateAllocated + "]";
	}

}
