/**
 * 
 */
package com.yahoo.petermwenda83.server.balance.webservice;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author pmnjeru
 *
 */
@XmlRootElement
public class WsRequest {
	
	private String requestType;
	private String account;
	private String admNo;
	private String password;//admNo+account+shared_password encryped

	/**
	 * 
	 */
	public WsRequest() {
		requestType = "";
		account = "";
		admNo = "";
		password = "";
	}

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the admNo
	 */
	public String getAdmNo() {
		return admNo;
	}

	/**
	 * @param admNo the admNo to set
	 */
	public void setAdmNo(String admNo) {
		this.admNo = admNo;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WsRequest [requestType=" + requestType + ", account=" + account + ", admNo=" + admNo + ", password="
				+ password + "]";
	}
	
}
