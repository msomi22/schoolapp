/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */

package ke.co.qubintel.school.server.bean.chat;

import java.sql.Timestamp;
import java.util.Date;

import ke.co.qubintel.school.server.bean.StorableBean;

/** 
 * A chat object
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Chat extends StorableBean{
	
	private String senderId;
	private String receiverId;
	private String message;
    private String isRead;
    private Timestamp dateSent;
   
	/**
	 * 
	 */
	public Chat() {
		senderId = "";
		receiverId = "";
		message = "";
		isRead = "";
		dateSent = new Timestamp(new Date().getTime());
		
	}
	
	/**
	 * @return the senderId
	 */
	public String getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return the receiverId
	 */
	public String getReceiverId() {
		return receiverId;
	}

	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
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
	 * @return the isRead
	 */
	public String getIsRead() {
		return isRead;
	}

	/**
	 * @param isRead the isRead to set
	 */
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the dateSent
	 */
	public Timestamp getDateSent() {
		return dateSent;
	}

	/**
	 * @param dateSent the dateSent to set
	 */
	public void setDateSent(Timestamp dateSent) {
		this.dateSent = dateSent;
	}
	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Chat [senderId=" + senderId + ", receiverId=" + receiverId + ", message=" + message + ", isRead="
				+ isRead + ", dateSent=" + dateSent + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId()
				+ "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 3172623208049289146L;

}
