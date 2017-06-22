/**
 * 
 */
package com.yahoo.petermwenda83.bean.account;

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
@Table( name = "smsapi" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class SmsApi extends StorableBeanById{

	private String  apiKey;
	private String apiPassword;

	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	private String uuid;	

	/**
	 * 
	 */
	public SmsApi() {
		apiKey = "";
		apiPassword = "";

		account = new Account(); 
		
		uuid = UUID.randomUUID().toString();
	}


	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return the apiPassword
	 */
	public String getApiPassword() {
		return apiPassword;
	}

	/**
	 * @param apiPassword the apiPassword to set
	 */
	public void setApiPassword(String apiPassword) {
		this.apiPassword = apiPassword;
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
			SmsApi type = (SmsApi)obj;

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
		return "SmsApi [apiKey=" + apiKey + ", apiPassword=" + apiPassword + ", account=" + account.getUsername() + ", uuid=" + uuid
				+ ", getId()=" + getId() + "]";
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = -4541201103610508045L;
}
