/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class ApiSysConfig {
	
	private String uuid;
	private String accountId;
	private String examId;
	private String term;
	private String year;
	private String cansendSMS;

	/**
	 * 
	 */
	public ApiSysConfig() {
		uuid = "";
		accountId = "";
		examId = "";
		term = "";
		year = "";
		cansendSMS = "";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
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

	public String getCansendSMS() {
		return cansendSMS;
	}

	public void setCansendSMS(String cansendSMS) {
		this.cansendSMS = cansendSMS;
	}

	@Override
	public String toString() {
		return "ApiSysConfig [uuid=" + uuid + ", accountId=" + accountId + ", examId=" + examId + ", term=" + term
				+ ", year=" + year + ", cansendSMS=" + cansendSMS + "]";
	}

}
