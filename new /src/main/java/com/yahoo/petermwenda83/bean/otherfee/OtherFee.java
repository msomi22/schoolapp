/**
 * 
 */
package com.yahoo.petermwenda83.bean.otherfee;

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
@Table( name = "otherfee" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class OtherFee extends StorableBeanByUUID{
	
	
	private String description;
	private int amount;
	private String term;
	private String year;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	/**
	 * 
	 */
	public OtherFee() {
		description = "";
		amount = 0;
		term = "";
		year = "";
		
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
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
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
		return "OtherFee [description=" + description + ", amount=" + amount + ", term=" + term + ", year=" + year
				+ ", account=" + account.getUsername() + ", getUuid()=" + getUuid() + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
