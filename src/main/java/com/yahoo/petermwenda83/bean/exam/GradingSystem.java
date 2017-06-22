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
import com.yahoo.petermwenda83.bean.subject.Category;

/** 
 *  A grading system in a school
 *  
 *@author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 */
@Entity
@Table( name = "gradingsystem" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class GradingSystem extends StorableBeanByUUID{

	private int lowerLimit;
	private int upperLimit;
	private String description;
	private int points;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="categoryId", referencedColumnName="uuid")
	private Category category;
	
	/**
	 * 
	 */
	public GradingSystem() {
		lowerLimit = 0;
		upperLimit = 0;
		description = "";
		points = 0;
		
		account = new Account();
		category = new Category();
	}


	/**
	 * @return the lowerLimit
	 */
	public int getLowerLimit() {
		return lowerLimit;
	}


	/**
	 * @param lowerLimit the lowerLimit to set
	 */
	public void setLowerLimit(int lowerLimit) {
		this.lowerLimit = lowerLimit;
	}


	/**
	 * @return the upperLimit
	 */
	public int getUpperLimit() {
		return upperLimit;
	}


	/**
	 * @param upperLimit the upperLimit to set
	 */
	public void setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}


	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
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
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}


	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GradingSystem [lowerLimit=" + lowerLimit + ", upperLimit=" + upperLimit + ", description=" + description
				+ ", points=" + points + ", account=" + account + ", category=" + category + ", getUuid()=" + getUuid()
				+ "]";
	}



	/**  
	 * 
	 */
	private static final long serialVersionUID = 8278712625087888708L;

}
