/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.jwt;

import java.util.Date;

/**
 * @author peter
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		String id = "38EFA2D4-352D-4BC0-887F-9CA227950501";
		String issuer = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		String subject = "principal";
		long ttlMillis = System.currentTimeMillis();// 0;
		
		Date date= new Date();
		
		ApiCredentials apiKey = new ApiCredentials();

		String jwtString = JWT.createJWT(id, issuer, subject, ttlMillis, apiKey.getSecret());

		System.out.println(jwtString); 

		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzOEVGQTJENC0zNTJELTRCQzAtODg3Ri05Q0EyMjc5NTA1MDEiLCJpYXQiOjE1MDI4Njk3NTUsInN1YiI6InByaW5jaXBhbCIsImlzcyI6IkUzQ0RDNTc4LTM3QkEtNENEQi1CMTUwLURBQjA0MDkyNzBDRCIsImV4cCI6MzAwNTczOTUxMH0._nyq-JF8lt8kAyzwb4bpIphMD4hHHM3TIWpiYLPb0OU";
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean valid = JWT.validateJWT(jwt, apiKey.getSecret(), id, issuer, subject);
		
		System.out.println(valid); 
		



	}

}
