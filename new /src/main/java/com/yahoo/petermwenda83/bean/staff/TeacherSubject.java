
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
package com.yahoo.petermwenda83.bean.staff;

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
import com.yahoo.petermwenda83.bean.classroom.Stream;
import com.yahoo.petermwenda83.bean.subject.Subject;


/** 
 * Teacher Subject-ClassRoom Allocation
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */

@Entity
@Table( name = "teachersubject" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TeacherSubject extends StorableBeanByUUID {
	
	private Timestamp allocationDate;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="teacherId", referencedColumnName="uuid")
	private Staff staff;
	
	@ManyToOne
	@JoinColumn(name="subjectId", referencedColumnName="uuid")
	private Subject subject;
	
	
	@ManyToOne
	@JoinColumn(name="streamId", referencedColumnName="uuid")
	private Stream stream;
	
	
	/**
	 * 
	 */
	public TeacherSubject() {
		super();
		allocationDate = new Timestamp(new Date().getTime());  
		
		account = new Account();
		staff = new Staff();
		subject = new Subject();
		stream = new Stream();
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
	 * @return the staff
	 */
	public Staff getStaff() {
		return staff;
	}

	/**
	 * @param staff the staff to set
	 */
	public void setStaff(Staff staff) {
		this.staff = staff;
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
	 * @return the stream
	 */
	public Stream getStream() {
		return stream;
	}

	/**
	 * @param stream the stream to set
	 */
	public void setStream(Stream stream) {
		this.stream = stream;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		TeacherSubject teacherSubject;
		
		if(obj instanceof TeacherSubject) {
			teacherSubject = (TeacherSubject) obj;
			
			return getUuid().equals(teacherSubject.getUuid());
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
		return "TeacherSubject [allocationDate=" + allocationDate + ", account=" + account.getUsername() + ", staff=" + staff.getUsername()
				+ ", subject=" + subject.getCode() + ", stream=" + stream.getDescription() + ", getUuid()=" + getUuid() + "]";
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = -8748801928170289528L;
}
