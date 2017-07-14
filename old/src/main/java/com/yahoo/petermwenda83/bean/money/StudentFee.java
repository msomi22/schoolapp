/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

import java.sql.Timestamp;
import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class StudentFee extends StorableBean{

	private String studentId;
	private int amountPaid;
	private String payMode;
	private String transactionId;
	private String paidHas;
	private Timestamp datePaid;

	private int amountTokenizer;


	public StudentFee() {
		studentId = "";
		amountPaid = 0;
		payMode = "";
		transactionId = "";
		paidHas = "";
		datePaid = new Timestamp(new Date().getTime());

		amountTokenizer = 0;

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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentFee [studentId=" + studentId + ", amountPaid=" + amountPaid + ", payMode=" + payMode
				+ ", transactionId=" + transactionId + ", paidHas=" + paidHas + ", datePaid=" + datePaid
				+ ", amountTokenizer=" + amountTokenizer + ", getUuid()=" + getUuid() + ", getAccountId()="
				+ getAccountId() + "]";
	}



	/** 
	 * 
	 */
	private static final long serialVersionUID = -4526710616012197088L;

}
