/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */
@XmlRootElement(name = "SubjectClass")  //only needed if we also want to generate XML 
public class SubClass {
	
	private String teacherId;
	private String subjectId;
	private String streamId;
	private String accountId;

	/**
	 * 
	 */
	public SubClass() {
		teacherId ="";
		subjectId ="";
		streamId ="";
		accountId = "";
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubClass [teacherId=" + teacherId + ", subjectId=" + subjectId + ", streamId=" + streamId
				+ ", accountId=" + accountId + "]";
	}
	
	

}
