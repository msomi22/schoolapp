/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author peter
 *
 */
@XmlRootElement(name = "FeeResponse") 
public class FeeResponse {
	
	@JsonProperty
	private StudentPayFee StudentPayFee;
	@JsonProperty
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
