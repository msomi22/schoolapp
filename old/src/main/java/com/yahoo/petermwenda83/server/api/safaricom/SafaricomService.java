/**
 * 
 */
package com.yahoo.petermwenda83.server.api.safaricom;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.yahoo.petermwenda83.server.api.rest.JsonFromObj;
import com.yahoo.petermwenda83.server.api.rest.bean.SubClass;
import com.yahoo.petermwenda83.server.api.safaricom.bean.AccountBalance;

/**
 * @author peter
 *
 */
public class SafaricomService {


	
/**
 * 
 * @param url Safariocm Account Balance Resource URL
 * @param username Consumer Key as provided by Safaricom
 * @param password Consumer Secret as provided by Safaricom
 * @return Result in form of JSON 
 */
	public static String getBalance(String url,String username,String password) {

		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		
		/**
		 * Base64 encoded string of the:
		 *  a) M-Pesa short code and 
		 *  b) password, 
		 *  which is encrypted using M-Pesa public key and validates the transaction on M-Pesa Core system.
		 */
		String SecurityCredential = "pSSoqpsVhavqrOzCLSeZ7T7R80tVAc75Y+tpwnqAKg6yd00vXKjS7wz/4/K8kPCSlrgtcfoTpTYXKPpnTnguKD7hYLqxlPiGniNAYKU57yAm6PRXhoYMy/Evz946uomKVhmiFwxZFH4HnGr/w7tImMIqQQK1/F8KD7XC4PUTG36T6Es3I+20H8wCeiyqUmmU0/WlD2502jAdwOH4543VEujiuagZO2uVgHWnJzfllioKPRhmXblR7yDAYGhpq4vl/G/k1zGzg3sznj3tKkiqIxc/aSnAHoaS37crHHrarxbOwtedqErSK9aeXTq13CM3BDMVobZ1SdNTWYUDb2mN7w==";
       
		AccountBalance balance = new AccountBalance();
		balance.setInitiator("testapi0321");
		balance.setSecurityCredential(SecurityCredential); 
		balance.setCommandID("AccountBalance"); 
		balance.setPartyA("600321");
		balance.setIdentifierType("4");
		balance.setRemarks("Cheking Balance");
		String domain = "http://3782cd77.ngrok.io";
		balance.setQueueTimeOutURL(domain+"/school/webapi/account/timeout");
		balance.setResultURL(domain+"/school/webapi/account/balance");
		
		String query = JsonFromObj.getJsonStringFromObject(balance); 
		
		System.out.println(query);
		
		url = "https://sandbox.safaricom.co.ke/oauth/v1/generate";
		String authEncoded = getAccessToken(url,username,password);

		 // POST method
        ClientResponse response = webResource
        		                    .accept("application/json")	
        		                    .header("Authorization", "Bearer " + authEncoded)
                                    .type("application/json")
                                    .post(ClientResponse.class, query);

		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);

		return output;
	}


	/**
	 * 
	 * @param url Safaricom URL for generating access token
	 * @param username Consumer Key as provided by Safaricom
	 * @param password Consumer Secret as provided by Safaricom
	 * @return Auth2 access token 
	 */
	public static String getAccessToken(String url,String username,String password) {
	
		String authEncoded = getAuthBase64(username,password);
		
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		ClientResponse resp = webResource.queryParam("grant_type", "client_credentials")
				.accept("application/json")
				.header("Authorization", "Basic " + authEncoded)
				.get(ClientResponse.class);


		if(resp.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = resp.getEntity(String.class);
		
		Gson g = new Gson(); 
		Token token = g.fromJson(output, Token.class);
		
		return token.getAccess_token(); 
	}
	
	/**
	 *
	 * @param username Consumer Key as provided by Safaricom
	 * @param password Consumer Secret as provided by Safaricom
	 * @return a base64 encoded String
	 */
	public static String getAuthBase64(String username,String password) {
		String authEncoded = "";
		String auth = username + ":" + password;
		try {

			authEncoded = Base64.getEncoder().encodeToString(auth.getBytes("utf-8")); 

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return authEncoded;
	}
	
	
	
	/**
	 * 
	 * @author peter
	 *
	 */
	static class Token{
		private String access_token;
		private String expires_in;
		
		public Token(){
			access_token = "";
			expires_in = "";
		}

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public String getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(String expires_in) {
			this.expires_in = expires_in;
		}

		@Override
		public String toString() {
			return "Token [access_token=" + access_token + ", expires_in=" + expires_in + "]";
		}
		
	}
	
	
	
	/**
	 * API TEST CODE 
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public static String schoolTest(String url,String username,String password) {

		String authEncoded = getAuthBase64(username,password);

		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		
		SubClass subclass = new SubClass();
		subclass.setAccountId("E3CDC578-37BA-4CDB-B150-DAB0409270CD");
		subclass.setStreamId("D3733507-C113-4795-91ED-D3CD8039EA03");
		subclass.setSubjectId("F1972BF2-C788-4F41-94FE-FBA1869C92BC");
		subclass.setTeacherId("5498156A-FE83-43F4-9592-737HDHJ877S"); 


		String query = JsonFromObj.getJsonStringFromObject(subclass); 

        // POST method
        ClientResponse response = webResource
        		                    .accept("application/json")	
        		                    .header("Authorization", "Basic " + authEncoded)
                                    .type("application/json")
                                    .post(ClientResponse.class, query);




		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response.getEntity(String.class);

		return output;
	}



}
