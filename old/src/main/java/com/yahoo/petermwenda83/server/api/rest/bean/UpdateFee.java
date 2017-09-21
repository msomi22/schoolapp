/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class UpdateFee {
	
	private String accountId;
	private String studentId;
	private String paymentId;
	private int previousAmount;
	private int correctAmount;

	/**
	 * 
	 */
	public UpdateFee() {
		accountId = "";
		studentId = "";
		paymentId = "";
		previousAmount = 0;
		correctAmount = 0;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public int getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(int previousAmount) {
		this.previousAmount = previousAmount;
	}

	public int getCorrectAmount() {
		return correctAmount;
	}

	public void setCorrectAmount(int correctAmount) {
		this.correctAmount = correctAmount;
	}

	@Override
	public String toString() {
		return "UpdateFee [accountId=" + accountId + ", studentId=" + studentId + ", paymentId=" + paymentId
				+ ", previousAmount=" + previousAmount + ", correctAmount=" + correctAmount + "]";
	}

	
}
