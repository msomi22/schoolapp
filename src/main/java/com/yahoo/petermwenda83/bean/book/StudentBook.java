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
import com.yahoo.petermwenda83.bean.student.Student;

/** 
 * This class represent a studentBook object, an object that contains the book
 *  borrowed and the student 
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
@Entity
@Table( name = "studentkook" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class StudentBook extends StorableBeanByUUID{
	
	private String hasReturned;
	private String returnDate;
	private Timestamp borrowDate;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="bookId", referencedColumnName="uuid")
    private Book book;
	
	@ManyToOne
	@JoinColumn(name="studentId", referencedColumnName="uuid")
    private Student student;

	/**
	 * 
	 */
	public StudentBook() {
		hasReturned = "";
		returnDate = "";
		borrowDate = new Timestamp(new Date().getTime()); 
		
		account = new Account();
		book = new Book();
		student = new Student();
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
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}


	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}


	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}


	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}


	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		StudentBook studentBook;
		
		if(obj instanceof StudentBook) {
			studentBook = (StudentBook) obj;
			
			return getUuid().equals(studentBook.getUuid());
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

	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentBook [hasReturned=" + hasReturned + ", returnDate=" + returnDate + ", borrowDate=" + borrowDate
				+ ", account=" + account.getUsername() + ", book=" + book.getAuthor() + ", student=" + student.getRegNo() + ", getUuid()=" + getUuid() + "]";
	}








	/**
	 * 
	 */
	private static final long serialVersionUID = 1660873636733409249L;

}
