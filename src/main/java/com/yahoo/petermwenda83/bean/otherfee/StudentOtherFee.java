/**
 * 
 */
package com.yahoo.petermwenda83.bean.otherfee;

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
 * @author peter
 *
 */
@Entity
@Table( name = "studentotherFee" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class StudentOtherFee extends StorableBeanByUUID{

	private int amountPiad;
	private String payMode;
	private Timestamp datePaid;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="studentId", referencedColumnName="uuid")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="otherFeeId", referencedColumnName="uuid")
	private OtherFee otherFee;
	
	/**
	 * 
	 */
	public StudentOtherFee() {
		amountPiad =0;
		payMode = "";
		datePaid = new Timestamp(new Date().getTime());
		
		account = new Account();
		student = new Student();
		otherFee = new OtherFee();
	}
	
	
	/**
	 * @return the amountPiad
	 */
	public int getAmountPiad() {
		return amountPiad;
	}

	/**
	 * @param amountPiad the amountPiad to set
	 */
	public void setAmountPiad(int amountPiad) {
		this.amountPiad = amountPiad;
	}

	/**
	 * @return the payMode
	 */
	public String getPayMode() {
		return payMode;
	}

	/**
	 * @param payMode the payMode to set
	 */
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	/**
	 * @return the datePaid
	 */
	public Timestamp getDatePaid() {
		return datePaid;
	}

	/**
	 * @param datePaid the datePaid to set
	 */
	public void setDatePaid(Timestamp datePaid) {
		this.datePaid = datePaid;
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
	 * @return the otherFee
	 */
	public OtherFee getOtherFee() {
		return otherFee;
	}


	/**
	 * @param otherFee the otherFee to set
	 */
	public void setOtherFee(OtherFee otherFee) {
		this.otherFee = otherFee;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentOtherFee [amountPiad=" + amountPiad + ", payMode=" + payMode + ", datePaid=" + datePaid
				+ ", account=" + account + ", student=" + student + ", otherFee=" + otherFee + ", getUuid()="
				+ getUuid() + "]";
	}



	/**
		 * 
		 */
		private static final long serialVersionUID = -300014716973010890L;
}
