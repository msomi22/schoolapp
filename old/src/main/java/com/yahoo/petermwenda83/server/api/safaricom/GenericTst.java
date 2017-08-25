/**
 * 
 */
package com.yahoo.petermwenda83.server.api.safaricom;

/**
 * @author peter
 *
 */
public class GenericTst {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*String url = "https://sandbox.safaricom.co.ke/oauth/v1/generate";
		String balUrl = "https://sandbox.safaricom.co.ke/mpesa/accountbalance/v1/query";
		String username = "mwendapeter72@gmail.com";
		String password = "peter*#@"; */
		
		//System.out.print(Generic.initQuery(url, username, password));
		//System.out.print(Generic.getBalance(balUrl, username, password));
		
		String staffId = "5498156A-FE83-43F4-9592-737HDHJ877S";
		
		//http://localhost:8080/school/webapi/staff/5498156A-FE83-43F4-9592-737HDHJ877S/subjects
		//http://localhost:8080/school/webapi/staff/5498156A-FE83-43F4-9592-737HDHJ877S/subjects

		String schoolUrl = "http://localhost:8080/school/webapi/staff/"+staffId+"/subjects";
		String username2 = "demo";
		String password2 = "12345678";
		System.out.println();
		System.out.println(Generic.schoolTest(schoolUrl, username2, password2));
		
	}

}
