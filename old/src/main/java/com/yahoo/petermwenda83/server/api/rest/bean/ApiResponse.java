/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */
@XmlRootElement(name = "ApiResponse")  //only needed if we also want to generate XML     
public class ApiResponse {
	
	private String message;
	private String description;
	
	public ApiResponse(){
		
	}

	public ApiResponse(String message){//"error"
		this.message = message;
		description = "User not authenticated";
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApiResponse [message=" + message + ", description=" + description + "]";
	}
	
	
	
}
