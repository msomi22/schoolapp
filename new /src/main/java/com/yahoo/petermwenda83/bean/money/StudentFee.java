/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

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
@Table( name = "studentfee" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class StudentFee extends StorableBeanByUUID{

	private int amountPaid;
	private String payMode;
	private String transactionId;
	private String paidHas;
	private Timestamp datePaid;

	private int amountTokenizer;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="studentId", referencedColumnName="uuid")
	private Student student;


	public StudentFee() {
		amountPaid = 0;
		payMode = "";
		transactionId = "";
		paidHas = "";
		datePaid = new Timestamp(new Date().getTime());

		amountTokenizer = 0;
		
		account = new Account();
		student = new Student();

	}

	/**
	 * @return the amountPaid
	 */
	public int getAmountPaid() {
		return amountPaid;
	}



	/**
	 * @param amountPaid the amountPaid to set
	 */
	public void setAmountPaid(int amountPaid) {
		this.amountPaid = amountPaid;
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
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}



	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}



	/**
	 * @return the paidHas
	 */
	public String getPaidHas() {
		return paidHas;
	}



	/**
	 * @param paidHas the paidHas to set
	 */
	public void setPaidHas(String paidHas) {
		this.paidHas = paidHas;
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
	 * @return the amountTokenizer
	 */
	public int getAmountTokenizer() {
		return amountTokenizer;
	}



	/**
	 * @param amountTokenizer the amountTokenizer to set
	 */
	public void setAmountTokenizer(int amountTokenizer) {
		this.amountTokenizer = amountTokenizer;
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
		return "StudentFee [amountPaid=" + amountPaid + ", payMode=" + payMode + ", transactionId=" + transactionId
				+ ", paidHas=" + paidHas + ", datePaid=" + datePaid + ", amountTokenizer=" + amountTokenizer
				+ ", account=" + account + ", student=" + student + ", getUuid()=" + getUuid() + "]";
	}





	/** 
	 * 
	 */
	private static final long serialVersionUID = -4526710616012197088L;

}
