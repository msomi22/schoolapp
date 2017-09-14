/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "SmsExams")
public class SmsExams {
	
	private String examCode;

	/**
	 * 
	 */
	public SmsExams() {
		examCode = "";
	}

	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	@Override
	public String toString() {
		return "SmsExams [examCode=" + examCode + "]";
	}

}
