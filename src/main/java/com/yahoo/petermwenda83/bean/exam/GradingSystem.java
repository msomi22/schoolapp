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
 *  A grading system in a school
 *  
 *@author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 */
public class GradingSystem extends StorableBean{
	
	private String categoryId ;
	private int lowerLimit;
	private int upperLimit;
	private String description;
	private int points;
	
	/**
	 * 
	 */
	public GradingSystem() {
		categoryId = "";
		lowerLimit = 0;
		upperLimit = 0;
		description = "";
		points = 0;
	}


	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}


	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	/**
	 * @return the lowerLimit
	 */
	public int getLowerLimit() {
		return lowerLimit;
	}


	/**
	 * @param lowerLimit the lowerLimit to set
	 */
	public void setLowerLimit(int lowerLimit) {
		this.lowerLimit = lowerLimit;
	}


	/**
	 * @return the upperLimit
	 */
	public int getUpperLimit() {
		return upperLimit;
	}


	/**
	 * @param upperLimit the upperLimit to set
	 */
	public void setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
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
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}


	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GradingSystem [categoryId=" + categoryId + ", lowerLimit=" + lowerLimit + ", upperLimit=" + upperLimit
				+ ", description=" + description + ", points=" + points + ", getUuid()=" + getUuid()
				+ ", getAccountId()=" + getAccountId() + "]";
	}


	/**  
	 * 
	 */
	private static final long serialVersionUID = 8278712625087888708L;

}
