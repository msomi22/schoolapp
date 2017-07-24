/**
 * 
 */
package com.yahoo.petermwenda83.bean.schoolaccount;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class SmsApi extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4541201103610508045L;
	private String  schoolAccountUuid;
	private String  apiKey;
	private String apiPassword;

	/**
	 * 
	 */
	public SmsApi() {
		schoolAccountUuid = "";
		apiKey = "";
		apiPassword = "";
	}
	
	/**
	 * @return the schoolAccountUuid
	 */
	public String getSchoolAccountUuid() {
		return schoolAccountUuid;
	}

	/**
	 * @param schoolAccountUuid the schoolAccountUuid to set
	 */
	public void setSchoolAccountUuid(String schoolAccountUuid) {
		this.schoolAccountUuid = schoolAccountUuid;
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

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("SMSAPI");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",schoolAccountUuid=");
		builder.append(schoolAccountUuid);
		builder.append(",apiKey=");
		builder.append(apiKey);
		builder.append(",apiPassword=");
		builder.append(apiPassword);
		builder.append("]");
		return builder.toString(); 
		}

}
