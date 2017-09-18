/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class FeeBreakdown extends StorableBean{

	private String feeCategory;
	private String feeCode;
	private String feeDescription;
	private int  amount;
	private String term;
	private String year;

	/**
	 * 
	 */
	public FeeBreakdown() {
		feeCategory = "";
		feeCode = "";
		feeDescription = "";
		amount = 0;
		term = "";
		year = "";
	}

	public String getFeeCategory() {
		return feeCategory;
	}

	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
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

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "FeeBreakdown [feeCategory=" + feeCategory + ", feeCode=" + feeCode + ", feeDescription="
				+ feeDescription + ", amount=" + amount + ", term=" + term + ", year=" + year + ", getUuid()="
				+ getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1571034939933518933L;

}
