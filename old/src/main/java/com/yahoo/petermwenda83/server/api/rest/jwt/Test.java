/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.jwt;

/**
 * @author peter
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {



		String id = "100";
		String issuer = "peter";
		String subject = "subject";
		long ttlMillis = System.currentTimeMillis();
		ApiCredentials apiKey = new ApiCredentials();

		String jwtString = JWT.createJWT(id, issuer, subject, ttlMillis, apiKey.getSecret());

		System.out.println(jwtString); 

		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDAiLCJpYXQiOjE1MDI4MDQ4MTUsInN1YiI6InN1YmplY3QiLCJpc3MiOiJwZXRlciIsImV4cCI6MzAwNTYwOTYzMX0.2634bvVWA8GaE3vWpS9HZrgxafs13-04uQ5rG9pilqo";
		
		boolean valid = JWT.validateJWT(jwtString, apiKey.getSecret(), id, issuer, subject);
		
		System.out.println(valid); 
		



	}

}
