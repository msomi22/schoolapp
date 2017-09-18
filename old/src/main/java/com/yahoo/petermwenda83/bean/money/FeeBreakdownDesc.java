/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class FeeBreakdownDesc  extends StorableBean{
	
	private String feeBreakdownId;
	private String feeCode;
	private String feeDescription;
	private int  amount;
	

	/**
	 * 
	 */
	public FeeBreakdownDesc() {
		feeBreakdownId = "";
		feeCode = "";
		feeDescription = "";
		amount = 0;
	}


	public String getFeeBreakdownId() {
		return feeBreakdownId;
	}


	public void setFeeBreakdownId(String feeBreakdownId) {
		this.feeBreakdownId = feeBreakdownId;
	}


	public String getFeeCode() {
		return feeCode;
	}


	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}


	public String getFeeDescription() {
		return feeDescription;
	}


	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		return "FeeBreakdownDesc [feeBreakdownId=" + feeBreakdownId + ", feeCode=" + feeCode + ", feeDescription="
				+ feeDescription + ", amount=" + amount + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -9194125259091484704L;
}
