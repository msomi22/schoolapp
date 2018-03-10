/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */

package ke.co.qubintel.school.server.bean.exam;

import ke.co.qubintel.school.server.bean.StorableBean;

/** 
 * Exam/Term configuration object
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class SysConfig extends StorableBean{
	
	/**
	 * 
	 */
	
	private String examId;
	private String term;
	private String year;
	private String cansendSMS;
	
	/**
	 * 
	 */
	public SysConfig() {
		examId = "";
		term = "";
		year = "";
		cansendSMS = "0";
	}

	
	
	/**
	 * @return the examId
	 */
	public String getExamId() {
		return examId;
	}



	/**
	 * @param examId the examId to set
	 */
	public void setExamId(String examId) {
		this.examId = examId;
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
	 * @return the cansendSMS
	 */
	public String getCansendSMS() {
		return cansendSMS;
	}



	/**
	 * @param cansendSMS the cansendSMS to set
	 */
	public void setCansendSMS(String cansendSMS) {
		this.cansendSMS = cansendSMS;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SysConfig [examId=" + examId + ", term=" + term + ", year=" + year + ", cansendSMS=" + cansendSMS
				+ ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}



	private static final long serialVersionUID = -2906872526805032876L;
}
