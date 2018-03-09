/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.bean.book;

import java.sql.Timestamp;
import java.util.Date;

import ke.co.qubintel.school.server.bean.StorableBean;

/** 
 * This class represent a studentBook object, an object that contains the book
 *  borrowed and the student 
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentBook extends StorableBean{
	
	private String studentId;
	private String bookId;
	private String hasReturned;
	private String returnDate;
	private Timestamp borrowDate;

	/**
	 * 
	 */
	public StudentBook() {
		studentId = "";
		bookId = "";
		hasReturned = "";
		returnDate = "";
		borrowDate = new Timestamp(new Date().getTime()); 
		
	}
	

	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return studentId;
	}


	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	/**
	 * @return the bookId
	 */
	public String getBookId() {
		return bookId;
	}


	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}


	/**
	 * @return the hasReturned
	 */
	public String getHasReturned() {
		return hasReturned;
	}


	/**
	 * @param hasReturned the hasReturned to set
	 */
	public void setHasReturned(String hasReturned) {
		this.hasReturned = hasReturned;
	}


	/**
	 * @return the returnDate
	 */
	public String getReturnDate() {
		return returnDate;
	}


	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}


	/**
	 * @return the borrowDate
	 */
	public Timestamp getBorrowDate() {
		return borrowDate;
	}


	/**
	 * @param borrowDate the borrowDate to set
	 */
	public void setBorrowDate(Timestamp borrowDate) {
		this.borrowDate = borrowDate;
	}


	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentBook [studentId=" + studentId + ", bookId=" + bookId + ", hasReturned=" + hasReturned
				+ ", returnDate=" + returnDate + ", borrowDate=" + borrowDate + ", getUuid()=" + getUuid()
				+ ", getAccountId()=" + getAccountId() + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1660873636733409249L;

}
