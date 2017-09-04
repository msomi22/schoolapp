/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "ApiSubject") 
public class ApiSubject {
	
	@JsonProperty
	private String uuid;
	@JsonProperty
	private String accountId;
	@JsonProperty
	private String studentId;
	@JsonProperty
	private String subjectId;

	/**
	 * 
	 */
	public ApiSubject() {
		uuid = "";
		accountId = "";
		studentId = "";
		subjectId = "";
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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "ApiSubject [uuid=" + uuid + ", accountId=" + accountId + ", studentId=" + studentId + ", subjectId="
				+ subjectId + "]";
	}

}
