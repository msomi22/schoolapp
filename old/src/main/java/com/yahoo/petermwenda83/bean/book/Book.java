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

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * A book object in the school
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Book extends StorableBean{
	
	  private String isbn;
	  private String author;
	  private String publisher;
	  private String title;
	  private String isAvailable;
	  private String category;
	  private Timestamp dateAdded;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", author=" + author + ", publisher=" + publisher + ", title=" + title
				+ ", isAvailable=" + isAvailable + ", category=" + category + ", dateAdded=" + dateAdded
				+ ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}

	/**
		 * 
		 */
		private static final long serialVersionUID = 2519971559271467654L;
}
