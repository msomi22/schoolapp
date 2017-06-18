
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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;
import com.yahoo.petermwenda83.bean.account.Account;

/**
 * Student's Primary Account Informations
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
@Entity
@Table( name = "studentprimary" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class StudentPrimary extends StorableBeanByUUID {

	private String schoolName;
	private String index;
	private String kcpeyear;
	private String kcpemark;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="studentId", referencedColumnName="uuid")
	private Student student;
	
	
	
	/**
	 * 
	 */
	public StudentPrimary() {
		super();
		schoolName ="";
		index ="";
		kcpeyear ="";
		kcpemark ="";
		
		account = new Account();
		student = new Student();
	}

	

	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}



	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}



	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}



	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}



	/**
	 * @return the kcpeyear
	 */
	public String getKcpeyear() {
		return kcpeyear;
	}



	/**
	 * @param kcpeyear the kcpeyear to set
	 */
	public void setKcpeyear(String kcpeyear) {
		this.kcpeyear = kcpeyear;
	}



	/**
	 * @return the kcpemark
	 */
	public String getKcpemark() {
		return kcpemark;
	}



	/**
	 * @param kcpemark the kcpemark to set
	 */
	public void setKcpemark(String kcpemark) {
		this.kcpemark = kcpemark;
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
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		StudentPrimary studentPrimary;
		
		if(obj instanceof StudentPrimary) {
			studentPrimary = (StudentPrimary) obj;
			
			return getUuid().equals(studentPrimary.getUuid());
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
		return "StudentPrimary [schoolName=" + schoolName + ", index=" + index + ", kcpeyear=" + kcpeyear
				+ ", kcpemark=" + kcpemark + ", account=" + account.getUsername() + ", student=" + student.getRegNo() + ", getUuid()="
				+ getUuid() + "]";
	}





	/**  
	 * 
	 */
	private static final long serialVersionUID = 46794661890236283L;
}
