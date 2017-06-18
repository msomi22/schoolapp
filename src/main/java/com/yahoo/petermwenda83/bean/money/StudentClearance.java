/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class StudentClearance extends StorableBean{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -583687680547772623L;
	 String studentUuid;
	 double clearingAmount;
	 String term;
	 String year;

	/**
	 * 
	 */
	public StudentClearance() {
		studentUuid = "";
		clearingAmount = 0;
		term = "";
		year = "";
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
	 * @return the clearingAmount
	 */
	public double getClearingAmount() {
		return clearingAmount;
	}

	/**
	 * @param clearingAmount the clearingAmount to set
	 */
	public void setClearingAmount(double clearingAmount) {
		this.clearingAmount = clearingAmount;
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
		builder.append("Clearance");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(",studentUuid =");
		builder.append(studentUuid);
		builder.append(", clearingAmount =");
		builder.append(clearingAmount);
		builder.append(", term =");
		builder.append(term);
		builder.append(", year =");
		builder.append(year);
		builder.append("]");
		return builder.toString(); 
		}

}
