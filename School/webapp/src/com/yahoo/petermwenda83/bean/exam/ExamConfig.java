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
 * Exam/Term configuration object
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ExamConfig extends StorableBean{
	
	/**
	 * 
	 */
	
	private String schoolAccountUuid;
	private String Term;
	private String Year;
	private String Exam;
	private String ExamMode;
	private String eTFone;
	private String eT;
	private String eTCtwo;
	private String eTConetwo;
	private String c1c2;
	private String sendSMS;

	/**
	 * 
	 */
	public ExamConfig() {
		schoolAccountUuid = "";
		Term = "";
		Year = "";
		Exam = "";
		ExamMode = "";
		eTFone = "";
		eT = "";
		eTCtwo = "";
		eTConetwo = "";
		c1c2 = "";
		sendSMS = "";
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
	 * @return the term
	 */
	public String getTerm() {
		return Term;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		Term = term;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return Year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		Year = year;
	}

	/**
	 * @return the exam
	 */
	public String getExam() {
		return Exam;
	}

	/**
	 * @param exam the exam to set
	 */
	public void setExam(String exam) {
		Exam = exam;
	}
   
	/**
	 * @return the examMode
	 */
	public String getExamMode() {
		return ExamMode;
	}

	/**
	 * @param examMode the examMode to set
	 */
	public void setExamMode(String examMode) {
		ExamMode = examMode;
	}
	 

	/**
	 * @return the eTFone
	 */
	public String geteTFone() {
		return eTFone;
	}

	/**
	 * @param eTFone the eTFone to set
	 */
	public void seteTFone(String eTFone) {
		this.eTFone = eTFone;
	}

	/**
	 * @return the eT
	 */
	public String geteT() {
		return eT;
	}

	/**
	 * @param eT the eT to set
	 */
	public void seteT(String eT) {
		this.eT = eT;
	}

	/**
	 * @return the eTCtwo
	 */
	public String geteTCtwo() {
		return eTCtwo;
	}

	/**
	 * @param eTCtwo the eTCtwo to set
	 */
	public void seteTCtwo(String eTCtwo) {
		this.eTCtwo = eTCtwo;
	}

	/**
	 * @return the eTConetwo
	 */
	public String geteTConetwo() {
		return eTConetwo;
	}

	/**
	 * @param eTConetwo the eTConetwo to set
	 */
	public void seteTConetwo(String eTConetwo) {
		this.eTConetwo = eTConetwo;
	}

	/**
	 * @return the c1c2
	 */
	public String getC1c2() {
		return c1c2;
	}

	/**
	 * @param c1c2 the c1c2 to set
	 */
	public void setC1c2(String c1c2) {
		this.c1c2 = c1c2;
	}

	/**
	 * @return the sendSMS
	 */
	public String getSendSMS() {
		return sendSMS;
	}

	/**
	 * @param sendSMS the sendSMS to set
	 */
	public void setSendSMS(String sendSMS) {
		this.sendSMS = sendSMS;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("ExamConfigDAO [ getUuid() = ");
		builder.append(getUuid());
		builder.append(", schoolAccountUuid=");
		builder.append(schoolAccountUuid);
		builder.append(", Term=");
		builder.append(Term);
		builder.append(", Year=");
		builder.append(Year);
		builder.append(", termAmount=");
		builder.append(Exam);
		builder.append(", ExamMode=");
		builder.append(ExamMode);//eTFone,eT,eTCtwo,eTConetwo
		builder.append(", eTFone=");
		builder.append(eTFone);
		builder.append(", eT=");
		builder.append(eT);
		builder.append(", eTCtwo=");
		builder.append(eTCtwo);
		builder.append(", eTConetwo=");
		builder.append(eTConetwo);
		builder.append(", c1c2=");
		builder.append(c1c2);
		builder.append(", sendSMS=");
		builder.append(sendSMS);
		builder.append("]");
		return builder.toString(); 
		}
	
	
	private static final long serialVersionUID = -2906872526805032876L;
}
