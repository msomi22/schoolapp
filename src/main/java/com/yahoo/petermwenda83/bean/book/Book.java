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

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * A book object in the school
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Book extends StorableBean{
	
	
	  String schoolAccountUuid;
	  String ISBN;
	  String author;
	  String publisher;
	  String title;
	  String bookStatus;
	  String borrowStatus;

	/**
	 * 
	 */
	public Book() {
		 schoolAccountUuid = "";
		 ISBN = "";
		 author = "";
		 publisher = "";
		 title = "";
		 bookStatus = "";
		 borrowStatus = "";
	}
	
	
	
	
	/**
	 * @return the schoolAccountUuid
	 */
	public String getSchoolAccountUuid() {
		return schoolAccountUuid;
	}




	/**
	 * @param schoolAccountUuid the schoolAccountUuid to set
	 */
	public void setSchoolAccountUuid(String schoolAccountUuid) {
		this.schoolAccountUuid = schoolAccountUuid;
	}




	/**
	 * @return the iSBN
	 */
	public String getISBN() {
		return ISBN;
	}




	/**
	 * @param iSBN the iSBN to set
	 */
	public void setISBN(String iSBN) {
		ISBN = iSBN;
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
	 * @return the bookStatus
	 */
	public String getBookStatus() {
		return bookStatus;
	}




	/**
	 * @param bookStatus the bookStatus to set
	 */
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}




	/**
	 * @return the borrowStatus
	 */
	public String getBorrowStatus() {
		return borrowStatus;
	}




	/**
	 * @param borrowStatus the borrowStatus to set
	 */
	public void setBorrowStatus(String borrowStatus) {
		this.borrowStatus = borrowStatus;
	}




	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("StudentBook");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",schoolAccountUuid=");
		builder.append(schoolAccountUuid);
		builder.append(",ISBN=");
		builder.append(ISBN);
		builder.append(",author=");
		builder.append(author);
		builder.append(",publisher=");
		builder.append(publisher);
		builder.append(",title=");
		builder.append(title);
		builder.append(",bookStatus=");
		builder.append(bookStatus);
		builder.append(",borrowStatus=");
		builder.append(borrowStatus);
		builder.append("]");
		return builder.toString(); 
		}
	
	  /**
		 * 
		 */
		private static final long serialVersionUID = 2519971559271467654L;
}
