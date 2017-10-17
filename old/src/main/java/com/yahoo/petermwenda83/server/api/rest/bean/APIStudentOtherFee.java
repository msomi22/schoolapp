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
	private String description;
	private String termPiad; 
	private String dateAllocated;
	

	/**
	 * 
	 */
	public APIStudentOtherFee() {
		otherFeeId = "";
		amount = "";
		description = "";
		termPiad = "";
		dateAllocated = "";
		
	}


	/**
	 * @return the otherFeeId
	 */
	public String getOtherFeeId() {
		return otherFeeId;
	}


	/**
	 * @param otherFeeId the otherFeeId to set
	 */
	public void setOtherFeeId(String otherFeeId) {
		this.otherFeeId = otherFeeId;
	}


	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}


	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the termPiad
	 */
	public String getTermPiad() {
		return termPiad;
	}


	/**
	 * @param termPiad the termPiad to set
	 */
	public void setTermPiad(String termPiad) {
		this.termPiad = termPiad;
	}


	/**
	 * @return the dateAllocated
	 */
	public String getDateAllocated() {
		return dateAllocated;
	}


	/**
	 * @param dateAllocated the dateAllocated to set
	 */
	public void setDateAllocated(String dateAllocated) {
		this.dateAllocated = dateAllocated;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "APIStudentOtherFee [otherFeeId=" + otherFeeId + ", amount=" + amount + ", description=" + description
				+ ", termPiad=" + termPiad + ", dateAllocated=" + dateAllocated + "]";
	}

	
}
