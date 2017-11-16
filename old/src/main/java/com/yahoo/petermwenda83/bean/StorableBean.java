/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.bean;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

/***
 * This class represents an object in the School System architecture that can be
 * stored in the RDBMS as well as cached.
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 * @author <a href="mailto:michael@tawi.mobi">Michael Wakahe</a>
 *
 */
public class StorableBean implements Serializable {

	@JsonIgnore
	private int id;
	private String uuid;
	private String accountId;  
	/**
	 * 
	 */
	public StorableBean() {
		id = 0;
		uuid = UUID.randomUUID().toString();
		accountId = "";
	} 
	/**
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * 
	 * @param uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 6756966885024490524L;
}
