
/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package com.yahoo.petermwenda83.bean.student;

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
import com.yahoo.petermwenda83.bean.subject.Subject;

/** 
 * Student's Subject-Class Allocation 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 * 
 */
@Entity
@Table( name = "studentsubject" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class StudentSubject extends StorableBeanByUUID {

	private Timestamp allocationDate;

	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;

	@ManyToOne
	@JoinColumn(name="studentId", referencedColumnName="uuid")
	private Student student;

	@ManyToOne
	@JoinColumn(name="subjectId", referencedColumnName="uuid")
	private Subject subject;

	public StudentSubject(){
		super();
		allocationDate = new Timestamp(new Date().getTime());

		account = new Account();
		student = new Student();
		subject = new Subject();
	}

	/**
	 * @return the allocationDate
	 */
	public Timestamp getAllocationDate() {
		return allocationDate;
	}

	/**
	 * @param allocationDate the allocationDate to set
	 */
	public void setAllocationDate(Timestamp allocationDate) {
		this.allocationDate = allocationDate;
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
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		StudentSubject studentSubject;
		
		if(obj instanceof StudentSubject) {
			studentSubject = (StudentSubject) obj;
			
			return getUuid().equals(studentSubject.getUuid());
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
		return "StudentSubject [allocationDate=" + allocationDate + ", account=" + account.getUsername() + ", student=" + student.getRegNo()
				+ ", subject=" + subject.getCode() + ", getUuid()=" + getUuid() + "]";
	}




	private static final long serialVersionUID = 1L;
}
