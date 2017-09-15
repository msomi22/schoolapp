/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class RevertedFee {
	
	private String otherFeeId;
	private String amount;
	private String dateReverted;

	/**
	 * 
	 */
	public RevertedFee() {
		otherFeeId = "";
		amount = "";
		dateReverted = "";
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

	public String getDateReverted() {
		return dateReverted;
	}

	public void setDateReverted(String dateReverted) {
		this.dateReverted = dateReverted;
	}

	@Override
	public String toString() {
		return "RevertedFee [otherFeeId=" + otherFeeId + ", amount=" + amount + ", dateReverted=" + dateReverted + "]";
	}

}
