/**
 * 
 */
package com.yahoo.petermwenda83.server.api.safaricom.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yahoo.petermwenda83.server.api.safaricom.Result;

/**
 * @author peter
 *
 */

//@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "SafResponse") 
public class SafResponse {
	
	@JsonProperty
	private Result Result;

	/**
	 * 
	 */
	public SafResponse() {
		Result = new Result();
	}

	public Result getResult() {
		return Result;
	}

	public void setResult(Result result) {
		Result = result;
	}

	
	@Override
	public String toString() {
		return "SafResponse [Result=" + Result + "]";
	}
	
	

}
