/**
 * 
 */
package com.yahoo.petermwenda83.server.balanace.webservice;

import javax.xml.ws.Endpoint;

/**
 * @author peter
 *
 */
public class StudentBalancePublisher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Endpoint.publish("http://localhost:8080/school/balance", new GetStudentBalance());  
		//wsimport -s . http://localhost:8080/school/balance?wsdl  

	}

}
