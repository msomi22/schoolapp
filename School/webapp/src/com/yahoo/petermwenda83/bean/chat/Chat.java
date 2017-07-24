/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.bean.chat;

import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * A chat object
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Chat extends StorableBean{
	
	private String senderUuid;
	private String receiverUuid;
	private String message;
	private Date dateSent;
    private String mgsStatus;

	/**
	 * 
	 */
	public Chat() {
		senderUuid = "";
		receiverUuid = "";
		message = "";
		dateSent = new Date();
		mgsStatus = "";
	}
	
	
	/**
	 * @return the senderUuid
	 */
	public String getSenderUuid() {
		return senderUuid;
	}


	/**
	 * @param senderUuid the senderUuid to set
	 */
	public void setSenderUuid(String senderUuid) {
		this.senderUuid = senderUuid;
	}


	/**
	 * @return the receiverUuid
	 */
	public String getReceiverUuid() {
		return receiverUuid;
	}


	/**
	 * @param receiverUuid the receiverUuid to set
	 */
	public void setReceiverUuid(String receiverUuid) {
		this.receiverUuid = receiverUuid;
	}


	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * @return the dateSent
	 */
	public Date getDateSent() {
		return new Date(dateSent.getTime());
	}


	/**
	 * @param dateSent the dateSent to set
	 */
	public void setDateSent(Date date) {
		if(date != null) {
			dateSent = new Date(date.getTime());
		}	
	}


	/**
	 * @return the mgsStatus
	 */
	public String getMgsStatus() {
		return mgsStatus;
	}


	/**
	 * @param mgsStatus the mgsStatus to set
	 */
	public void setMgsStatus(String mgsStatus) {
		this.mgsStatus = mgsStatus;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Chat");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",senderUuid=");
		builder.append(senderUuid);
		builder.append(",receiverUuid=");
		builder.append(receiverUuid);
		builder.append(",message=");
		builder.append(message);
		builder.append(",dateSent=");
		builder.append(dateSent);
		builder.append(",mgsStatus=");
		builder.append(mgsStatus);
		builder.append("]");
		return builder.toString(); 
		}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3172623208049289146L;

}
