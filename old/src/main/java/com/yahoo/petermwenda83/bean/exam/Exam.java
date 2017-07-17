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
 * An exam in a school
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Exam extends StorableBean{
	
	private String code;
	private String description;
	private int outOf;
	
	/**
	 * 
	 */
	public Exam() {
		super();
		code ="";
		description ="";
		outOf = 0;
	}
	

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the outOf
	 */
	public int getOutOf() {
		return outOf;
	}


	/**
	 * @param outOf the outOf to set
	 */
	public void setOutOf(int outOf) {
		this.outOf = outOf;
	}


	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Exam [code=" + code + ", description=" + description + ", outOf=" + outOf + ", getUuid()=" + getUuid()
				+ ", getAccountId()=" + getAccountId() + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 2625893752188549331L;
	
	
}
