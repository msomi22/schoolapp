/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.bean.exam;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class BarWeight extends StorableBean{
	
	private String studentId;
	private String year;
	private double meanOne;
	private double meanTwo;
	private double meanhree;
	
	public BarWeight() {
		studentId = "";
		year = "";
		meanOne = 0;
		meanTwo = 0;
		meanhree = 0;
	}
	
	
	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return studentId;
	}


	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
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
	 * @return the meanOne
	 */
	public double getMeanOne() {
		return meanOne;
	}


	/**
	 * @param meanOne the meanOne to set
	 */
	public void setMeanOne(double meanOne) {
		this.meanOne = meanOne;
	}


	/**
	 * @return the meanTwo
	 */
	public double getMeanTwo() {
		return meanTwo;
	}


	/**
	 * @param meanTwo the meanTwo to set
	 */
	public void setMeanTwo(double meanTwo) {
		this.meanTwo = meanTwo;
	}


	/**
	 * @return the meanhree
	 */
	public double getMeanhree() {
		return meanhree;
	}


	/**
	 * @param meanhree the meanhree to set
	 */
	public void setMeanhree(double meanhree) {
		this.meanhree = meanhree;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BarWeight [studentId=" + studentId + ", year=" + year + ", meanOne=" + meanOne + ", meanTwo=" + meanTwo
				+ ", meanhree=" + meanhree + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 3611433316960505196L;

}
