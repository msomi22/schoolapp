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
	
	 /** 
	 * 
	 */
	private static final long serialVersionUID = 3556481789932595293L;
	private String schoolAccountUuid;
	private String term;
	private String year;
	private double termAmount;
	private double dayAmount;
	/** 
	 * 
	 */
	public TermFee() {
		schoolAccountUuid = "";
		term = "";
		year = "";
		termAmount = 0.0;
		dayAmount = 0.0;

	}
	
	
	
	/**
	 * @return the schoolAccountUuid
	 */
	public String getSchoolAccountUuid() {
		return schoolAccountUuid;
	}



	/**
	 * @param schoolAccountUuid the schoolAccountUuid to set
	 */
	public void setSchoolAccountUuid(String schoolAccountUuid) {
		this.schoolAccountUuid = schoolAccountUuid;
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
	 * @return the termAmount
	 */
	public double getTermAmount() {
		return termAmount;
	}



	/**
	 * @param termAmount the termAmount to set
	 */
	public void setTermAmount(double termAmount) {
		this.termAmount = termAmount;
	}

   


	/**
	 * @return the dayAmount
	 */
	public double getDayAmount() {
		return dayAmount;
	}



	/**
	 * @param dayAmount the dayAmount to set
	 */
	public void setDayAmount(double dayAmount) {
		this.dayAmount = dayAmount;
	}



	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Term Fee");
		builder.append(",[getUuid() = ");
		builder.append(getUuid());
		builder.append(", schoolAccountUuid =");
		builder.append(schoolAccountUuid); 
		builder.append(", term =");
		builder.append(term);
		builder.append(", year =");
		builder.append(year);
		builder.append(", termAmount =");
		builder.append(termAmount);
		builder.append(", dayAmount =");
		builder.append(dayAmount);
		builder.append("]");
		return builder.toString(); 
		}

}
