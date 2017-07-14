/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.bean.book;

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
import com.yahoo.petermwenda83.bean.account.SmsApi;

/** 
 * A book object in the school
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */

@Entity
@Table( name = "book" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Book extends StorableBeanByUUID{

	private String isbn;
	private String author;
	private String publisher;
	private String title;
	private String isAvailable;
	private String category;
	private Timestamp dateAdded;

	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;

	/**
	 * 
	 */
	public Book() {
		isbn = "";
		author = "";
		publisher = "";
		title = "";
		isAvailable = "";
		category = "";
		dateAdded = new Timestamp(new Date().getTime()); 
		
		account = new Account();
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the isAvailable
	 */
	public String getIsAvailable() {
		return isAvailable;
	}

	/**
	 * @param isAvailable the isAvailable to set
	 */
	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the dateAdded
	 */
	public Timestamp getDateAdded() {
		return dateAdded;
	}

	/**
	 * @param dateAdded the dateAdded to set
	 */
	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
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

		if(obj instanceof SmsApi) {	
			Book type = (Book)obj;

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

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", author=" + author + ", publisher=" + publisher + ", title=" + title
				+ ", isAvailable=" + isAvailable + ", category=" + category + ", dateAdded=" + dateAdded + ", account="
				+ account.getUsername() + ", getUuid()=" + getUuid() + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 2519971559271467654L;
}
