/**
 * 
 */
package com.yahoo.petermwenda83.bean.account;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class ApiCredential extends StorableBean{
	
	private String  apiType;
	private String  apiKey;
	private String apisecret;

	/**
	 * 
	 */
	public ApiCredential() {
		apiType = "";
		apiKey = "";
		apisecret = "";
	}
	
	public String getApiType() {
		return apiType;
	}

	public void setApiType(String apiType) {
		this.apiType = apiType;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	

	public String getApisecret() {
		return apisecret;
	}

	public void setApisecret(String apisecret) {
		this.apisecret = apisecret;
	}

	@Override
	public String toString() {
		return "ApiCredential [apiType=" + apiType + ", apiKey=" + apiKey + ", apisecret=" + apisecret + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4541201103610508045L;
}
