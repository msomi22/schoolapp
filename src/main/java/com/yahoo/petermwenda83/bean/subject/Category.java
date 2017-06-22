/**
 * 
 */
package com.yahoo.petermwenda83.bean.subject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;
import com.yahoo.petermwenda83.bean.account.Account;

/** 
 * @author peter
 *
 */
@Entity
@Table( name = "category" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Category extends StorableBeanByUUID {
	
	private String description;
    private int maxNo;
    
    @ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;

	/**
	 * 
	 */
	public Category() {
		description = "";
		maxNo = 0;
		
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
	 * @return the maxNo
	 */
	public int getMaxNo() {
		return maxNo;
	}

	/**
	 * @param maxNo the maxNo to set
	 */
	public void setMaxNo(int maxNo) {
		this.maxNo = maxNo;
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
		if (this == obj) return true;
		
		Category category;
		
		if(obj instanceof Category) {
			category = (Category) obj;
			
			return getUuid().equals(category.getUuid());
		}
		
		return false;
	}


	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getUuid().hashCode();
	}
	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [description=" + description + ", maxNo=" + maxNo + ", account=" + account.getUsername() + ", getUuid()="
				+ getUuid() + "]";
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1738398339745042349L;

}
