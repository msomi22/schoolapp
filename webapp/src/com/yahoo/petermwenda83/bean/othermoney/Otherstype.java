/**
 * 
 */
package com.yahoo.petermwenda83.bean.othermoney;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class Otherstype extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String schoolAccountUuid;
	String type;
	String term;
	String year;

	/**
	 * 
	 */
	public Otherstype() {
		type = "";
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Other Monies");
		builder.append(",[getUuid() = ");
		builder.append(getUuid());
		builder.append(", schoolAccountUuid =");
		builder.append(schoolAccountUuid); 
		builder.append(", type =");
		builder.append(type); 
		builder.append(", term =");
		builder.append(term); 
		builder.append(", year =");
		builder.append(year); 
		builder.append("]");
		return builder.toString(); 
		}
}
