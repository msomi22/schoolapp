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

@XmlRootElement(name = "ApiClass") 
public class ApiClass {
	
	@JsonProperty
	private String uuid;
	@JsonProperty
	private String accountId;
	@JsonProperty
	private String description;

	/**
	 * 
	 */
	public ApiClass() {
		uuid = "";
		accountId = "";
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ApiClass [uuid=" + uuid + ", accountId=" + accountId
				+ ", description=" + description + "]";
	}

}
