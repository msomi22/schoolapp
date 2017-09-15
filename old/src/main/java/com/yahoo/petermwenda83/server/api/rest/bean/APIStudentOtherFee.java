/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class APIStudentOtherFee {
	
	private String otherFeeId;
	private String amount;
	private String termPiad; 
	private String dateAllocated;
	

	/**
	 * 
	 */
	public APIStudentOtherFee() {
		otherFeeId = "";
		amount = "";
		termPiad = "";
		dateAllocated = "";
		
	}

	public String getOtherFeeId() {
		return otherFeeId;
	}

	public void setOtherFeeId(String otherFeeId) {
		this.otherFeeId = otherFeeId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTermPiad() {
		return termPiad;
	}

	public void setTermPiad(String termPiad) {
		this.termPiad = termPiad;
	}

	public String getDateAllocated() {
		return dateAllocated;
	}

	public void setDateAllocated(String dateAllocated) {
		this.dateAllocated = dateAllocated;
	}

	@Override
	public String toString() {
		return "APIStudentOtherFee [otherFeeId=" + otherFeeId + ", amount=" + amount + ", termPiad=" + termPiad
				+ ", dateAllocated=" + dateAllocated + "]";
	}

}
