/**
 * 
 */
package com.yahoo.petermwenda83.bean.account;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class SmsApi extends StorableBean{
	
	private String  apiKey;
	private String apiPassword;

	/**
	 * 
	 */
	public SmsApi() {
		apiKey = "";
		apiPassword = "";
	}
	
	
	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return the apiPassword
	 */
	public String getApiPassword() {
		return apiPassword;
	}

	/**
	 * @param apiPassword the apiPassword to set
	 */
	public void setApiPassword(String apiPassword) {
		this.apiPassword = apiPassword;
	}
	
	


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SmsApi [apiKey=" + apiKey + ", apiPassword=" + apiPassword + ", getUuid()=" + getUuid()
				+ ", getAccountId()=" + getAccountId() + "]";
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = -4541201103610508045L;
}
