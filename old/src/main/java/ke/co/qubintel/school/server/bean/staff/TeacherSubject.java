
/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package ke.co.qubintel.school.server.bean.staff;

import java.sql.Timestamp;
import java.util.Date;

import ke.co.qubintel.school.server.bean.StorableBean;

/**
 * Teacher Subject-ClassRoom Allocation
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */

public class TeacherSubject extends StorableBean {
	
	private String teacherId;
	private String subjectId;
	private String streamId;
	private Timestamp allocationDate;
	
	
	/**
	 * 
	 */
	public TeacherSubject() {
		super();
		teacherId ="";
		subjectId ="";
		streamId ="";
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
		return "TeacherSubject [teacherId=" + teacherId + ", subjectId=" + subjectId + ", streamId=" + streamId
				+ ", allocationDate=" + allocationDate + ", getUuid()=" + getUuid() + ", getAccountId()="
				+ getAccountId() + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -8748801928170289528L;
}
