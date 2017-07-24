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

	/**
	 * 
	 */
	private static final long serialVersionUID = 2625893752188549331L;
	
	private String examName;
	private String schoolAccountUuid;
	private int outOf;
	
	
	
	/**
	 * 
	 */
	public Exam() {
		super();
		examName ="";
		schoolAccountUuid ="";
		outOf = 0;
	}
	
	/**
	 * @return the examName
	 */
	public String getExamName() {
		return examName;
	}

	/**
	 * @param examName the examName to set
	 */
	public void setExamName(String examName) {
		this.examName = examName;
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

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Exam [ getUuid() = ");
		builder.append(getUuid());
		builder.append(",schoolAccountUuid=");
		builder.append(schoolAccountUuid);//outOf
		builder.append(",examName=");
		builder.append(examName);
		builder.append(",outOf=");
		builder.append(outOf);
		builder.append("]");
		return builder.toString(); 
		}
	
}
