/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class ApiClassTeacher {
	
	private String accountId;
	private String staffId;
	private String staffName;
	private String staffNo;
	
	private String streamId;
	private String streamDesc;
	

	/**
	 * 
	 */
	public ApiClassTeacher() {
		accountId = "";
		staffId = "";
		staffName = "";
		staffNo = "";
		streamId = "";
		streamDesc = "";
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
	 * @return the staffId
	 */
	public String getStaffId() {
		return staffId;
	}


	/**
	 * @param staffId the staffId to set
	 */
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	/**
	 * @return the staffName
	 */
	public String getStaffName() {
		return staffName;
	}


	/**
	 * @param staffName the staffName to set
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}


	/**
	 * @return the staffNo
	 */
	public String getStaffNo() {
		return staffNo;
	}


	/**
	 * @param staffNo the staffNo to set
	 */
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApiClassTeacher [accountId=" + accountId + ", staffId=" + staffId + ", staffName=" + staffName
				+ ", staffNo=" + staffNo + ", streamId=" + streamId + ", streamDesc=" + streamDesc + "]";
	}
	
	

}
