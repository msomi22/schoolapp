/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.bean.classroom;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;
import com.yahoo.petermwenda83.bean.account.Account;

/**
 *  A stream object in a school
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
@Entity
@Table( name = "classroom" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ClassRoom  extends StorableBeanByUUID{

	private String description;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	/**
	 * 
	 */
	public ClassRoom() {
		super();
		description ="";
		
		account = new Account();
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





	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClassRoom [description=" + description + ", account=" + account + ", getUuid()=" + getUuid() + "]";
	}





	/** 
	 *  
	 */
	private static final long serialVersionUID = 6259060888065917342L;
}
