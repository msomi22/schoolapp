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

@XmlRootElement(name = "teacherSubject")  //only needed if we also want to generate XML 

public class APITeacherSubject extends ApiResponse{
	
	
	private String teacherId;
	private String subjectId;
	private String streamId;
	private String uuid;
	private String accountId;
	private Timestamp allocationDate;

	/**
	 * 
	 */
	public APITeacherSubject() {
		teacherId ="";
		subjectId ="";
		streamId ="";
		uuid = "";
		accountId = "";
		allocationDate = new Timestamp(new Date().getTime());  
	}

	

	/**
	 * @return the teacherId
	 */
	public String getTeacherId() {
		return teacherId;
	}



	/**
	 * @param teacherId the teacherId to set
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}



	/**
	 * @return the subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}



	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}



	/**
	 * @return the streamId
	 */
	public String getStreamId() {
		return streamId;
	}



	/**
	 * @param streamId the streamId to set
	 */
	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}



	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}



	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}



	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}



	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}



	/**
	 * @return the allocationDate
	 */
	public Timestamp getAllocationDate() {
		return allocationDate;
	}



	/**
	 * @param allocationDate the allocationDate to set
	 */
	public void setAllocationDate(Timestamp allocationDate) {
		this.allocationDate = allocationDate;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "APITeacherSubject [teacherId=" + teacherId + ", subjectId=" + subjectId + ", streamId=" + streamId
				+ ", uuid=" + uuid + ", accountId=" + accountId + ", allocationDate=" + allocationDate + "]";
	}


}
