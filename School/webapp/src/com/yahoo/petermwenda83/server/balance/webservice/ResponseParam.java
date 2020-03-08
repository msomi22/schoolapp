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
public class ResponseParam {
	
	private String paramName;
	private String paramValue;

	/**
	 * 
	 */
	public ResponseParam() {
		paramName = "";
		paramValue = "";
	}

	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResponseParam [paramName=" + paramName + ", paramValue=" + paramValue + "]";
	}

}
