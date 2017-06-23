/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.bean.chat;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.staff.Staff;

/** 
 * A chat object
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
@Entity
@Table( name = "chat" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Chat extends StorableBeanByUUID{
	
	private String message;
    private String isRead;
    private Timestamp dateSent;
    
    @ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
    
    @ManyToOne
	@JoinColumn(name="senderId", referencedColumnName="uuid")
	private Staff staff;
    
    @ManyToOne
   	@JoinColumn(name="receiverId", referencedColumnName="uuid")
   	private Staff staff2;
   
	/**
	 * 
	 */
	public Chat() {
		message = "";
		isRead = "";
		dateSent = new Timestamp(new Date().getTime());
		
		account = new Account();
		staff = new Staff();
		staff2 = new Staff();
		
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the isRead
	 */
	public String getIsRead() {
		return isRead;
	}

	/**
	 * @param isRead the isRead to set
	 */
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the dateSent
	 */
	public Timestamp getDateSent() {
		return dateSent;
	}

	/**
	 * @param dateSent the dateSent to set
	 */
	public void setDateSent(Timestamp dateSent) {
		this.dateSent = dateSent;
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
	 * @return the staff
	 */
	public Staff getStaff() {
		return staff;
	}

	/**
	 * @param staff the staff to set
	 */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	/**
	 * @return the staff2
	 */
	public Staff getStaff2() {
		return staff2;
	}

	/**
	 * @param staff2 the staff2 to set
	 */
	public void setStaff2(Staff staff2) {
		this.staff2 = staff2;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Chat [message=" + message + ", isRead=" + isRead + ", dateSent=" + dateSent + ", account=" + account
				+ ", staff=" + staff + ", staff2=" + staff2 + ", getUuid()=" + getUuid() + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 3172623208049289146L;

}
