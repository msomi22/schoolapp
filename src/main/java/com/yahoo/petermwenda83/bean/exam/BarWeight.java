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
import com.yahoo.petermwenda83.bean.student.Student;

/**
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
@Entity
@Table( name = "barweight" ) 
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class BarWeight extends StorableBeanByUUID{

	private String year;
	private double meanOne;
	private double meanTwo;
	private double meanhree;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="studentId", referencedColumnName="uuid")
	private Student student;
	
	
	public BarWeight() {
		year = "";
		meanOne = 0;
		meanTwo = 0;
		meanhree = 0;
		
		account = new Account();
		student = new Student();
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
	 * @return the meanOne
	 */
	public double getMeanOne() {
		return meanOne;
	}


	/**
	 * @param meanOne the meanOne to set
	 */
	public void setMeanOne(double meanOne) {
		this.meanOne = meanOne;
	}


	/**
	 * @return the meanTwo
	 */
	public double getMeanTwo() {
		return meanTwo;
	}


	/**
	 * @param meanTwo the meanTwo to set
	 */
	public void setMeanTwo(double meanTwo) {
		this.meanTwo = meanTwo;
	}


	/**
	 * @return the meanhree
	 */
	public double getMeanhree() {
		return meanhree;
	}


	/**
	 * @param meanhree the meanhree to set
	 */
	public void setMeanhree(double meanhree) {
		this.meanhree = meanhree;
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
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}


	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BarWeight [year=" + year + ", meanOne=" + meanOne + ", meanTwo=" + meanTwo + ", meanhree=" + meanhree
				+ ", account=" + account + ", student=" + student + ", getUuid()=" + getUuid() + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 3611433316960505196L;

}
