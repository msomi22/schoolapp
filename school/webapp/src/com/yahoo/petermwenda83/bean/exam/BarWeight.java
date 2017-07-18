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
	

	private String schoolAccountUuid;
	private String studentUuid;
	private String term;
	private String year;
	private double weightOne;
	private double weightTwo;
	private double weightThree;

	/**
	 * 
	 */
	public BarWeight() {
		schoolAccountUuid = "";
		studentUuid = "";
		term = "";
		year = "";
		weightOne = 0;
		weightTwo = 0;
		weightThree = 0;
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
	 * @return the studentUuid
	 */
	public String getStudentUuid() {
		return studentUuid;
	}


	/**
	 * @param studentUuid the studentUuid to set
	 */
	public void setStudentUuid(String studentUuid) {
		this.studentUuid = studentUuid;
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
	 * @return the weightOne
	 */
	public double getWeightOne() {
		return weightOne;
	}


	/**
	 * @param weightOne the weightOne to set
	 */
	public void setWeightOne(double weightOne) {
		this.weightOne = weightOne;
	}


	/**
	 * @return the weightTwo
	 */
	public double getWeightTwo() {
		return weightTwo;
	}


	/**
	 * @param weightTwo the weightTwo to set
	 */
	public void setWeightTwo(double weightTwo) {
		this.weightTwo = weightTwo;
	}


	/**
	 * @return the weightThree
	 */
	public double getWeightThree() {
		return weightThree;
	}


	/**
	 * @param weightThree the weightThree to set
	 */
	public void setWeightThree(double weightThree) {
		this.weightThree = weightThree;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Weight [ getUuid() = ");
		builder.append(getUuid());
		builder.append(", schoolAccountUuid=");
		builder.append(schoolAccountUuid);
		builder.append(", studentUuid=");
		builder.append(studentUuid);
		builder.append(", term=");
		builder.append(term);
		builder.append(", year=");
		builder.append(year);
		builder.append(", weightOne=");
		builder.append(weightOne);
		builder.append(", weightTwo=");
		builder.append(weightTwo);
		builder.append(", weightThree=");
		builder.append(weightThree);
		builder.append("]");
		return builder.toString(); 
		}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3611433316960505196L;

}
