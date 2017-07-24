/**
 * 
 */
package com.yahoo.petermwenda83.bean.student;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class House extends StorableBean {
	
	/**
	 *  
	 */
	private static final long serialVersionUID = -6490144222304400994L;
	
	private String houseName;
	private String schoolAccountUuid;

	/**
	 * 
	 */
	public House() {
		super();
		houseName = "";
		schoolAccountUuid = "";
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
	 * @return the houseName
	 */
	public String getHouseName() {
		return houseName;
	}



	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}



	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("House");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", schoolAccountUuid");
		builder.append(schoolAccountUuid);
		builder.append(", houseName");
		builder.append(houseName);
		builder.append("]");
		return builder.toString(); 
		}
}
