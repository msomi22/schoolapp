/**
 * 
 */
package com.yahoo.petermwenda83.bean.account;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;

/** 
 * @author peter
 *
 */
/**
 * @author peter
 *
 */
@Entity
@Table( name = "miscellanous" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Miscellanous extends StorableBeanByUUID{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5808103297941824732L;
	
	private String key;
	private String value;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	

	/**
	 * 
	 */
	public Miscellanous() {
		key = ""; 
		value = "";
		
		account = new Account();
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;

		if(obj instanceof Miscellanous) {	
			Miscellanous type = (Miscellanous)obj;

			isEqual = type.getUuid().equals(getUuid());		
		}

		return isEqual;		
	}



	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getUuid().hashCode();
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Miscellanous [key=" + key + ", value=" + value + ", account=" + account.getUsername() + ", getUuid()=" + getUuid()
				+ "]";
	}

	
}
