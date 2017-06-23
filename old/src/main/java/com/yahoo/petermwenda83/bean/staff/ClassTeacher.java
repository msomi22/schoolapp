/**
 * 
 */
package com.yahoo.petermwenda83.bean.staff;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *  
 */
public class ClassTeacher extends StorableBean{

	private String teacherId;
	private String streamId;
	/**
	 * 
	 */
	public ClassTeacher() {
		teacherId = "";
		streamId = "";
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClassTeacher [teacherId=" + teacherId + ", streamId=" + streamId + ", getUuid()=" + getUuid()
		+ ", getAccountId()=" + getAccountId() + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -720546801232129197L;
}
