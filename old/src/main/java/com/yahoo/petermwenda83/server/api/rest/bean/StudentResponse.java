/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "StudentResponse") 
public class StudentResponse {
	
	@JsonProperty
	private StudentFeeAPI studentFeeAPI;
	@JsonProperty
	private ApiResponse apiResponse;

	public StudentResponse(){
		studentFeeAPI = new StudentFeeAPI();
		apiResponse = new ApiResponse();
	}

	public StudentFeeAPI getStudentFeeAPI() {
		return studentFeeAPI;
	}

	public void setStudentFeeAPI(StudentFeeAPI studentFeeAPI) {
		this.studentFeeAPI = studentFeeAPI;
	}

	public ApiResponse getApiResponse() {
		return apiResponse;
	}

	public void setApiResponse(ApiResponse apiResponse) {
		this.apiResponse = apiResponse;
	}

	@Override
	public String toString() {
		return "Response [studentFeeAPI=" + studentFeeAPI + ", apiResponse=" + apiResponse + "]";
	}

}