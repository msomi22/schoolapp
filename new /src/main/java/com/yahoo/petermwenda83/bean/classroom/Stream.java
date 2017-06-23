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
 *  A class object in a school
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
@Entity
@Table( name = "stream" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Stream extends StorableBeanByUUID{
	
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="classRoomId", referencedColumnName="uuid")
	private ClassRoom classRoom;
	

	/**
	 * 
	 */
	public Stream() {
		description = "";
		
		account = new Account();
		classRoom = new ClassRoom();
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


	/**
	 * @return the classRoom
	 */
	public ClassRoom getClassRoom() {
		return classRoom;
	}


	/**
	 * @param classRoom the classRoom to set
	 */
	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Stream [description=" + description + ", account=" + account + ", classRoom=" + classRoom
				+ ", getUuid()=" + getUuid() + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 2355498812294520397L;
	
}
