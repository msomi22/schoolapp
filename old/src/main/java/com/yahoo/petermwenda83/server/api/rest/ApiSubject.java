
package com.yahoo.petermwenda83.server.api.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "ApiSubject") 
public class ApiSubject {
	
	private String uuid;
	private String accountId;
	private String studentId;
	private String subjectId;
	private String description;

	/**
	 * 
	 */
	public ApiSubject() {
		uuid = "";
		accountId = "";
		studentId = "";
		subjectId = "";
		description = "";
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ApiSubject [uuid=" + uuid + ", accountId=" + accountId + ", studentId=" + studentId + ", subjectId="
				+ subjectId + ", description=" + description + "]";
	}

}