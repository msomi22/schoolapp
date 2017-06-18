/**
 * 
 */
package com.yahoo.petermwenda83.bean.schoolaccount;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class SmsSend extends StorableBean{
	
	
	   private String status;
	   private String phoneNo;
	   private String messageId;
	   private String cost;

	/**
	 * 
	 */
	public SmsSend() {
		 status = "";
		 phoneNo = "";
		 messageId = "";
		 cost = "";

	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the cost
	 */
	public String getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("SMS");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",status=");
		builder.append(status);
		builder.append(",phoneNo=");
		builder.append(phoneNo);
		builder.append(",messageId=");
		builder.append(messageId);
		builder.append(",cost=");
		builder.append(cost);
		builder.append("]");
		return builder.toString(); 
		}
	   /**
		 * 
		 */
		private static final long serialVersionUID = -7291438428747377588L;
}
