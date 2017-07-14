/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class TermFee extends StorableBean{
	
	
	private int boaderAmount;
	private int dayAmount;
	private String term;
	private String year;
	
	/** 
	 * 
	 */
	public TermFee() {
		boaderAmount = 0;
		dayAmount = 0;
		term = "";
		year = "";

	}
	
	 /**
	 * @return the boaderAmount
	 */
	public int getBoaderAmount() {
		return boaderAmount;
	}

	/**
	 * @param boaderAmount the boaderAmount to set
	 */
	public void setBoaderAmount(int boaderAmount) {
		this.boaderAmount = boaderAmount;
	}

	/**
	 * @return the dayAmount
	 */
	public int getDayAmount() {
		return dayAmount;
	}

	/**
	 * @param dayAmount the dayAmount to set
	 */
	public void setDayAmount(int dayAmount) {
		this.dayAmount = dayAmount;
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
		return "TermFee [boaderAmount=" + boaderAmount + ", dayAmount=" + dayAmount + ", term=" + term + ", year="
				+ year + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}

	/** 
	 * 
	 */
	private static final long serialVersionUID = 3556481789932595293L;

}
