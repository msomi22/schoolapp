/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "APISubjectClasss")  //only needed if we also want to generate XML     
public class APISubjectClasss {
	
	private String teacherId;
	private String subjectId;
	private String subjectDesc;
	private String streamId;
	private String uuid;
	private String accountId;
	private Timestamp allocationDate;
	
	

	/**
	 * 
	 */
	public APISubjectClasss() {
		teacherId ="";
		subjectId ="";
		subjectDesc = "";
		streamId ="";
		uuid = "";
		accountId = "";
		allocationDate = new Timestamp(new Date().getTime());  
	}



	public String getTeacherId() {
		return teacherId;
	}



	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}



	public String getSubjectId() {
		return subjectId;
	}



	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}



	public String getSubjectDesc() {
		return subjectDesc;
	}



	public void setSubjectDesc(String subjectDesc) {
		this.subjectDesc = subjectDesc;
	}



	public String getStreamId() {
		return streamId;
	}



	public void setStreamId(String streamId) {
		this.streamId = streamId;
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



	public Timestamp getAllocationDate() {
		return allocationDate;
	}



	public void setAllocationDate(Timestamp allocationDate) {
		this.allocationDate = allocationDate;
	}



	@Override
	public String toString() {
		return "APISubjectClasss [teacherId=" + teacherId + ", subjectId=" + subjectId + ", subjectDesc=" + subjectDesc
				+ ", streamId=" + streamId + ", uuid=" + uuid + ", accountId=" + accountId + ", allocationDate="
				+ allocationDate + "]";
	}


}
