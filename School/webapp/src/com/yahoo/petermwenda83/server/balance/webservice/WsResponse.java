/**
 * 
 */
package com.yahoo.petermwenda83.server.balance.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author pmnjeru
 *
 */
@XmlRootElement
public class WsResponse {
	
	
	private String admNo;
	private List<ResponseParam> responseParams;

	/**
	 * 
	 */
	public WsResponse() {
		admNo = "";
		responseParams = new ArrayList<>();
	}

	/**
	 * @return the admNo
	 */
	public String getAdmNo() {
		return admNo;
	}

	/**
	 * @param admNo the admNo to set
	 */
	public void setAdmNo(String admNo) {
		this.admNo = admNo;
	}

	/**
	 * @return the responseParams
	 */
	public List<ResponseParam> getResponseParams() {
		return responseParams;
	}

	/**
	 * @param responseParams the responseParams to set
	 */
	public void setResponseParams(List<ResponseParam> responseParams) {
		this.responseParams = responseParams;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WsResponse [admNo=" + admNo + ", responseParams=" + responseParams + "]";
	}

}
