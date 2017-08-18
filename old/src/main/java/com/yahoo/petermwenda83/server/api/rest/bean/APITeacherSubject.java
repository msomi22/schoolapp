/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "APITeacherSubject")  //only needed if we also want to generate XML 
public class APITeacherSubject{
	
	
	private ApiResponse response;
	private APISubjectClasss apiSubjectClasss;
	
	public APITeacherSubject(){
		
	}

	/**
	 * 
	 */
	public APITeacherSubject(ApiResponse response,APISubjectClasss apiSubjectClasss) {
		
		this.response = response;
		this.apiSubjectClasss = apiSubjectClasss;
	
	}

	/**
	 * @return the response
	 */
	public ApiResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(ApiResponse response) {
		this.response = response;
	}

	/**
	 * @return the apiSubjectClasss
	 */
	public APISubjectClasss getApiSubjectClasss() {
		return apiSubjectClasss;
	}

	/**
	 * @param apiSubjectClasss the apiSubjectClasss to set
	 */
	public void setApiSubjectClasss(APISubjectClasss apiSubjectClasss) {
		this.apiSubjectClasss = apiSubjectClasss;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "APITeacherSubject [response=" + response + ", apiSubjectClasss=" + apiSubjectClasss + "]";
	}

	

}
