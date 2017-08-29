/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class StudentPayFee {
	
	private String accountId;
	private String regNo;
	private String staffId;
	private String paymentMode;
	private String transactionId;
	private String amount;
	private String term;
	private String year;
	private String refNo;
	private String feeBalance;

	/**
	 * 
	 */
	public StudentPayFee() {
		accountId = "";
		regNo = "";
		staffId = "";
		paymentMode = "";
		transactionId = "";
		amount = "";
		term = "";
		year = "";
		refNo = "";
		feeBalance = "";
		
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getFeeBalance() {
		return feeBalance;
	}

	public void setFeeBalance(String feeBalance) {
		this.feeBalance = feeBalance;
	}

	
	@Override
	public String toString() {
		return "StudentPayFee [accountId=" + accountId + ", regNo=" + regNo + ", staffId=" + staffId + ", paymentMode="
				+ paymentMode + ", transactionId=" + transactionId + ", amount=" + amount + ", term=" + term + ", year="
				+ year + ", refNo=" + refNo + ", feeBalance=" + feeBalance + "]";
	}
	

}
