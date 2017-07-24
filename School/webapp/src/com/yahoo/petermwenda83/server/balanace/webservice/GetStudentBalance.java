/**
 * 
 */
package com.yahoo.petermwenda83.server.balanace.webservice;

import javax.jws.WebService;

/**
 * @author peter
 *
 */

@WebService(endpointInterface="com.yahoo.petermwenda83.server.balanace.webservice.SchoolGetStudentBalance")
public class GetStudentBalance implements SchoolGetStudentBalance {

	@Override
	public String findStudentBalance(String schoolusername, String admissionNumber) {
		return "Username: " + schoolusername + ", Admission Number: " + admissionNumber;
	}

}
