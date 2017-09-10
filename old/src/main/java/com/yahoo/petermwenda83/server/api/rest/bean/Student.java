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

@XmlRootElement(name = "Student") 
public class Student {
	
	@JsonProperty
	private String uuid;
	
	/**
	 * 
	 */
	public Student() {
		uuid = "";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Student [uuid=" + uuid + "]";
	}


}
