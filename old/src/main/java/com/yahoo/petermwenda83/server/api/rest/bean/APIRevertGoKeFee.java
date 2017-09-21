/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class APIRevertGoKeFee {
	
	private String accountId;
	private String studentId;
	private String termPiad;
	private String yearPaid;

	/**
	 * 
	 */
	public APIRevertGoKeFee() {
		accountId = "";
		studentId = "";
		termPiad = "";
		yearPaid = "";
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

	public String getTermPiad() {
		return termPiad;
	}

	public void setTermPiad(String termPiad) {
		this.termPiad = termPiad;
	}

	public String getYearPaid() {
		return yearPaid;
	}

	public void setYearPaid(String yearPaid) {
		this.yearPaid = yearPaid;
	}

	@Override
	public String toString() {
		return "RevertGoKeFee [accountId=" + accountId + ", studentId=" + studentId + ", termPiad=" + termPiad
				+ ", yearPaid=" + yearPaid + "]";
	}
	

}
