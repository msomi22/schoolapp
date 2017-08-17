/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */
@XmlRootElement(name = "staff")  //only needed if we also want to generate XML 
public class GenericUser {
	
	private String logedUserId; 
	private String logedUserAccessId; 

	/**
	 * 
	 */
	public GenericUser() {
		logedUserId = "";
		logedUserAccessId = "";
	}

	/**
	 * @return the logedUserId
	 */
	public String getLogedUserId() {
		return logedUserId;
	}

	/**
	 * @param logedUserId the logedUserId to set
	 */
	public void setLogedUserId(String logedUserId) {
		this.logedUserId = logedUserId;
	}

	/**
	 * @return the logedUserAccessId
	 */
	public String getLogedUserAccessId() {
		return logedUserAccessId;
	}

	/**
	 * @param logedUserAccessId the logedUserAccessId to set
	 */
	public void setLogedUserAccessId(String logedUserAccessId) {
		this.logedUserAccessId = logedUserAccessId;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GenericUser [logedUserId=" + logedUserId + ", logedUserAccessId=" + logedUserAccessId + "]";
	}
	
	

}
