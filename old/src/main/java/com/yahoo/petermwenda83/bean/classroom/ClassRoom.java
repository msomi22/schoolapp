/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.bean.classroom;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 *  A stream object in a school
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ClassRoom  extends StorableBean{

	private String description;
	
	/**
	 * 
	 */
	public ClassRoom() {
		super();
		description ="";
		
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClassRoom [description=" + description + ", getUuid()=" + getUuid() + ", getAccountId()="
				+ getAccountId() + "]";
	}



	/** 
	 *  
	 */
	private static final long serialVersionUID = 6259060888065917342L;
}
