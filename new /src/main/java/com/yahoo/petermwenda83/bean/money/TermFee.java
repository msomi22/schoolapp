/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

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
@Table( name = "termfee" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TermFee extends StorableBeanByUUID{
	
	
	private int boaderAmount;
	private int dayAmount;
	private String term;
	private String year;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	/** 
	 * 
	 */
	public TermFee() {
		boaderAmount = 0;
		dayAmount = 0;
		term = "";
		year = "";
		
		account = new Account();

	}
	
	 /**
	 * @return the boaderAmount
	 */
	public int getBoaderAmount() {
		return boaderAmount;
	}

	/**
	 * @param boaderAmount the boaderAmount to set
	 */
	public void setBoaderAmount(int boaderAmount) {
		this.boaderAmount = boaderAmount;
	}

	/**
	 * @return the dayAmount
	 */
	public int getDayAmount() {
		return dayAmount;
	}

	/**
	 * @param dayAmount the dayAmount to set
	 */
	public void setDayAmount(int dayAmount) {
		this.dayAmount = dayAmount;
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
		return "TermFee [boaderAmount=" + boaderAmount + ", dayAmount=" + dayAmount + ", term=" + term + ", year="
				+ year + ", account=" + account + ", getUuid()=" + getUuid() + "]";
	}



	/** 
	 * 
	 */
	private static final long serialVersionUID = 3556481789932595293L;

}
