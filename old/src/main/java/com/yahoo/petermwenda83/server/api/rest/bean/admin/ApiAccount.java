/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean.admin;

import javax.xml.bind.annotation.XmlRootElement;

import com.yahoo.petermwenda83.bean.account.Account;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "ApiAccount") 
public class ApiAccount extends Account{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2784181069116126865L;

	/**
	 * 
	 */
	public ApiAccount() {
		super();
	}

	@Override
	public String toString() {
		return "ApiAccount [getIsActive()=" + getIsActive() + ", getName()=" + getName() + ", getMotto()=" + getMotto()
				+ ", getWebsite()=" + getWebsite() + ", getLogo()=" + getLogo() + ", getSignature()=" + getSignature()
				+ ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword() + ", getMobile()="
				+ getMobile() + ", getEmail()=" + getEmail() + ", getAddress()=" + getAddress() + ", getTown()="
				+ getTown() + ", getIsBoarding()=" + getIsBoarding() + ", getIsMixed()=" + getIsMixed()
				+ ", getLastUpdated()=" + getLastUpdated() + ", getCreationDate()=" + getCreationDate() + ", getUuid()="
				+ getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}

	
}
