/**
 * 
 */
package com.yahoo.petermwenda83.bean.exam;

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
 * @author peter
 *
 */
@Entity
@Table( name = "deviation" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Deviation extends StorableBeanByUUID{
	
	private String year;
	private double devOne;
	private double devTwo;
	private double devThree;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="studentId", referencedColumnName="uuid")
	private Student student;
	
	
	/**
	 * 
	 */
	public Deviation() {
		year = "";
		devOne = 0;
		devTwo = 0;
		devThree = 0;
		
		account = new Account();
		student = new Student();
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
	 * @return the devOne
	 */
	public double getDevOne() {
		return devOne;
	}

	/**
	 * @param devOne the devOne to set
	 */
	public void setDevOne(double devOne) {
		this.devOne = devOne;
	}

	/**
	 * @return the devTwo
	 */
	public double getDevTwo() {
		return devTwo;
	}

	/**
	 * @param devTwo the devTwo to set
	 */
	public void setDevTwo(double devTwo) {
		this.devTwo = devTwo;
	}

	/**
	 * @return the devThree
	 */
	public double getDevThree() {
		return devThree;
	}

	/**
	 * @param devThree the devThree to set
	 */
	public void setDevThree(double devThree) {
		this.devThree = devThree;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Deviation [year=" + year + ", devOne=" + devOne + ", devTwo=" + devTwo + ", devThree=" + devThree
				+ ", account=" + account + ", student=" + student + ", getUuid()=" + getUuid() + "]";
	}

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4304459493382193867L;
}
