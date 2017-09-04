/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "ApiStream") 
public class ApiStream {
	
	@JsonProperty
	private String uuid;
	@JsonProperty
	private String accountId;
	@JsonProperty
	private String classRoomId;
	@JsonProperty
	private String description;

	/**
	 * 
	 */
	public ApiStream() {
		uuid = "";
		accountId = "";
		classRoomId = "";
		description = "";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getClassRoomId() {
		return classRoomId;
	}

	public void setClassRoomId(String classRoomId) {
		this.classRoomId = classRoomId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ApiStream [uuid=" + uuid + ", accountId=" + accountId + ", classRoomId=" + classRoomId
				+ ", description=" + description + "]";
	}

}
