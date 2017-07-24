package com.yahoo.petermwenda83.server.balanace.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService 
public interface SchoolGetStudentBalance {
	@WebMethod
	public String findStudentBalance(String schoolusername,String admissionNumber);  

}
