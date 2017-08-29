/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class FeeResponse {
	
	private StudentPayFee StudentPayFee;
	private ApiResponse ApiResponse;

	/**
	 * 
	 */
	public FeeResponse() {
		ApiResponse = new ApiResponse();
		StudentPayFee = new StudentPayFee();
	}

	public StudentPayFee getStudentPayFee() {
		return StudentPayFee;
	}

	public void setStudentPayFee(StudentPayFee studentPayFee) {
		StudentPayFee = studentPayFee;
	}

	public ApiResponse getApiResponse() {
		return ApiResponse;
	}

	public void setApiResponse(ApiResponse apiResponse) {
		ApiResponse = apiResponse;
	}

	
	@Override
	public String toString() {
		return "FeeResponse [StudentPayFee=" + StudentPayFee + ", ApiResponse=" + ApiResponse + "]";
	}


}
