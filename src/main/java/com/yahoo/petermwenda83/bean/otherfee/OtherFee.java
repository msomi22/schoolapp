/**
 * 
 */
package com.yahoo.petermwenda83.bean.otherfee;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class OtherFee extends StorableBean{
	
	
	private String description;
	private int amount;
	private String term;
	private String year;
	
	/**
	 * 
	 */
	public OtherFee() {
		description = "";
		amount = 0;
		term = "";
		year = "";
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
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OtherFee [description=" + description + ", amount=" + amount + ", term=" + term + ", year=" + year
				+ ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
