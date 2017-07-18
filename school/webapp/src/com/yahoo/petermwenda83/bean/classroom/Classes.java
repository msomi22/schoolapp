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
 *  A class object in a school
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Classes extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2355498812294520397L;
	private String className = "";

	/**
	 * 
	 */
	public Classes() {
		className = "";
	}
   

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}


	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Classes");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",className=");
		builder.append(className);
		return builder.toString(); 
		}
}
