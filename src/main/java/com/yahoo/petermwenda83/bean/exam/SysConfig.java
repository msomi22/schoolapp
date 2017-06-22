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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;
import com.yahoo.petermwenda83.bean.account.Account;

/** 
 * Exam/Term configuration object
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
@Entity
@Table( name = "sysconfig" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class SysConfig extends StorableBeanByUUID{

	private String term;
	private String year;
	private String cansendSMS;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="examId", referencedColumnName="uuid")
	private Exam exam;
	
	
	
	/**
	 * 
	 */
	public SysConfig() {
		term = "";
		year = "";
		cansendSMS = "";
		
		account = new Account();
		exam = new Exam();
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
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the exam
	 */
	public Exam getExam() {
		return exam;
	}

	/**
	 * @param exam the exam to set
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}

	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SysConfig [term=" + term + ", year=" + year + ", cansendSMS=" + cansendSMS + ", account=" + account
				+ ", exam=" + exam + ", getUuid()=" + getUuid() + "]";
	}




	private static final long serialVersionUID = -2906872526805032876L;
}
