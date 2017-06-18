/**
 * 
 */
package com.yahoo.petermwenda83.bean.account;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanById;
/** 
 * @author peter
 *
 */
@Entity
@Table( name = "outgoingsms" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class OutGoingSMS extends StorableBeanById{

	private String status;
	private String mobile;
	private String message;
	private String smsCost;
	private Timestamp sendDate;
	
	private String uuid;	

	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;

	/**
	 * 
	 */
	public OutGoingSMS() {
		status = "";
		mobile = "";
		message = "";
		smsCost = "";
		sendDate = new Timestamp(new Date().getTime()); 
		
		uuid = UUID.randomUUID().toString();
		
		account = new Account();

	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}


	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	 * @return the smsCost
	 */
	public String getSmsCost() {
		return smsCost;
	}


	/**
	 * @param smsCost the smsCost to set
	 */
	public void setSmsCost(String smsCost) {
		this.smsCost = smsCost;
	}


	/**
	 * @return the sendDate
	 */
	public Timestamp getSendDate() {
		return sendDate;
	}


	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
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
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}


	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;

		if(obj instanceof SmsApi) {	
			OutGoingSMS type = (OutGoingSMS)obj;

			isEqual = type.getUuid().equals(uuid);		
		}

		return isEqual;		
	}



	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return uuid.hashCode();
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OutGoingSMS [status=" + status + ", mobile=" + mobile + ", message=" + message + ", smsCost=" + smsCost
				+ ", sendDate=" + sendDate + ", uuid=" + uuid + ", account=" + account.getUsername() + ", getId()=" + getId() + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -7291438428747377588L;
}
