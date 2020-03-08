/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

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
	private String streamDesc;
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
		streamDesc = "";
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
	 * @return the subjectDesc
	 */
	public String getSubjectDesc() {
		return subjectDesc;
	}



	/**
	 * @param subjectDesc the subjectDesc to set
	 */
	public void setSubjectDesc(String subjectDesc) {
		this.subjectDesc = subjectDesc;
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
	 * @return the streamDesc
	 */
	public String getStreamDesc() {
		return streamDesc;
	}



	/**
	 * @param streamDesc the streamDesc to set
	 */
	public void setStreamDesc(String streamDesc) {
		this.streamDesc = streamDesc;
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



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "APISubjectClasss [teacherId=" + teacherId + ", subjectId=" + subjectId + ", subjectDesc=" + subjectDesc
				+ ", streamId=" + streamId + ", streamDesc=" + streamDesc + ", uuid=" + uuid + ", accountId="
				+ accountId + ", allocationDate=" + allocationDate + "]";
	}




}
