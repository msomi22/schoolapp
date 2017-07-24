/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class PocketMoney extends StorableBean{
	
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
     
    
	private String studentUuid;
	private double amount;
	private String systemUser;
	private Date DateCommitted;
	private String term;
	private String year;

	/**
	 * 
	 */
	public PocketMoney() {
		studentUuid = "";
		amount = 0.0;
		systemUser = "";
		DateCommitted = new Date();
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
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the systemUser
	 */
	public String getSystemUser() {
		return systemUser;
	}

	/**
	 * @param systemUser the systemUser to set
	 */
	public void setSystemUser(String systemUser) {
		this.systemUser = systemUser;
	}

	/**
	 * @return the dateCommitted
	 */
	public Date getDateCommitted() {
		return DateCommitted;
	}

	/**
	 * @param dateCommitted the dateCommitted to set
	 */
	public void setDateCommitted(Date dateCommitted) {
		DateCommitted = dateCommitted;
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
		builder.append("Money");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid =");
		builder.append(studentUuid);
		builder.append(", amount =");
		builder.append(amount);
		builder.append(", term =");
		builder.append(term);
		builder.append(", year =");
		builder.append(year);
		builder.append("]");
		return builder.toString(); 
		}
}
