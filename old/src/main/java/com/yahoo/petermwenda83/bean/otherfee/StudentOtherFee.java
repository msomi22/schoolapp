/**
 * 
 */
package com.yahoo.petermwenda83.bean.otherfee;

import java.sql.Timestamp;
import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class StudentOtherFee extends StorableBean{
	
	
	private String studentId;
	private String otherFeeId;
	private int amountPiad;
	private String payMode;
	private String termPiad; 
	private String yearPaid;
	private Timestamp datePaid;
	
	/**
	 * 
	 */
	public StudentOtherFee() {
		studentId = "";
		otherFeeId = "";
		amountPiad =0;
		payMode = "";
		termPiad = "";
		yearPaid = "";
		datePaid = new Timestamp(new Date().getTime());
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
	 * @return the otherFeeId
	 */
	public String getOtherFeeId() {
		return otherFeeId;
	}



	/**
	 * @param otherFeeId the otherFeeId to set
	 */
	public void setOtherFeeId(String otherFeeId) {
		this.otherFeeId = otherFeeId;
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
	 * @return the termPiad
	 */
	public String getTermPiad() {
		return termPiad;
	}



	/**
	 * @param termPiad the termPiad to set
	 */
	public void setTermPiad(String termPiad) {
		this.termPiad = termPiad;
	}



	/**
	 * @return the yearPaid
	 */
	public String getYearPaid() {
		return yearPaid;
	}



	/**
	 * @param yearPaid the yearPaid to set
	 */
	public void setYearPaid(String yearPaid) {
		this.yearPaid = yearPaid;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentOtherFee [studentId=" + studentId + ", otherFeeId=" + otherFeeId + ", amountPiad=" + amountPiad
				+ ", payMode=" + payMode + ", termPiad=" + termPiad + ", yearPaid=" + yearPaid + ", datePaid="
				+ datePaid + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}



	/**
		 * 
		 */
		private static final long serialVersionUID = -300014716973010890L;
}
