/**
 * 
 */
package com.yahoo.petermwenda83.server.api.safaricom;

/**
 * @author peter
 *
 */
public class SafaricomServiceTest {


	/**
	 * @param args
	 */
	public static void main(String[] args) {



		//String url = "https://sandbox.safaricom.co.ke/oauth/v1/generate";
		String balUrl = "https://sandbox.safaricom.co.ke/mpesa/accountbalance/v1/query";
		String consumer_key = "Rwqrrjj4wV2UhgMZYtLMF2X8SQhci6TV";
		String consumer_secret = "GeGSs75GrGGa5zAv"; 

		//String consumer_Key = "Rwqrrjj4wV2UhgMZYtLMF2X8SQhci6TV";
		//String consumer_Secret = "GeGSs75GrGGa5zAv";


		//System.out.println(SafaricomService.getBalance(balUrl, consumer_key, consumer_secret)); 
		
		System.out.println(SafaricomService.SimulateRequest(consumer_key,consumer_secret)); 
		
		//System.out.println(SafaricomService.registerURLS(consumer_key,consumer_secret)); 
		
		//System.out.println(Generic.getAccessToken(url, username, password));

		//String staffId = "5498156A-FE83-43F4-9592-737HDHJ877S";


		/*String schoolUrl = "http://localhost:8080/school/webapi/staff/"+staffId+"/subjects";
		String username2 = "demo";
		String password2 = "12345678";
		System.out.println();
		System.out.println(Generic.schoolTest(schoolUrl, username2, password2));*/

	}



	
}
