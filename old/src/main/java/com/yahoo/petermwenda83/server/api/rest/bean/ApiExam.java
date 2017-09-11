/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class ApiExam {
	
	private String uuid;
	private String accountId;
	private String code;
	private String description;
	private int outOf;

	/**
	 * 
	 */
	public ApiExam() {
		uuid = "";
		accountId = "";
		code ="";
		description ="";
		outOf = 0;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOutOf() {
		return outOf;
	}

	public void setOutOf(int outOf) {
		this.outOf = outOf;
	}

	@Override
	public String toString() {
		return "ApiExam [uuid=" + uuid + ", accountId=" + accountId + ", code=" + code + ", description=" + description
				+ ", outOf=" + outOf + "]";
	}

}
