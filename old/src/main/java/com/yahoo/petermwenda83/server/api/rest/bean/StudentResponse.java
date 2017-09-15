/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "StudentResponse") 
public class StudentResponse {
	
	private StudentFeeAPI studentFeeAPI;
	
	public StudentResponse(){
		studentFeeAPI = new StudentFeeAPI();
		
	}

	public StudentFeeAPI getStudentFeeAPI() {
		return studentFeeAPI;
	}

	public void setStudentFeeAPI(StudentFeeAPI studentFeeAPI) {
		this.studentFeeAPI = studentFeeAPI;
	}


	@Override
	public String toString() {
		return "Response [studentFeeAPI=" + studentFeeAPI + "]";
	}

}