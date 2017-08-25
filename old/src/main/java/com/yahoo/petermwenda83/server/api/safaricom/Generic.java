/**
 * 
 */
package com.yahoo.petermwenda83.server.api.safaricom;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

/**
 * @author peter
 *
 */
public class Generic {


	public static String schoolTest(String url,String username,String password) {

		//
		String authEncoded = getAuthBase64(username,password);

		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);

		/*Form form = new Form();
		form.add("teacherId", "5498156A-FE83-43F4-9592-737HDHJ877S");    
		form.add("subjectId", "F1972BF2-C788-4F41-94FE-FBA1869C92BC");
		form.add("streamId", "D3733507-C113-4795-91ED-D3CD8039EA03");    
		form.add("accountId", "E3CDC578-37BA-4CDB-B150-DAB0409270CD");*/
		
		 String input = "{\"teacherId\": \"5498156A-FE83-43F4-9592-737HDHJ877S\", "
	                  + "\"subjectId\":\"F1972BF2-C788-4F41-94FE-FBA1869C92BC\","
	                  + "\"streamId\":\"D3733507-C113-4795-91ED-D3CD8039EA03\", "
	                  + "\"accountId\":\"E3CDC578-37BA-4CDB-B150-DAB0409270CD\"}";


		/*ClientResponse response = webResource
				.accept("application/json")		     
				.header("Authorization", "Basic " + authEncoded)
				.post(ClientResponse.class, form);*/
		
		 // POST method
        ClientResponse response2 = webResource.accept("application/json")
                .type("application/json").post(ClientResponse.class, input);
        
        

		if(response2.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = response2.getEntity(String.class);

		return output;
	}



	public static String getBalance(String url,String username,String password) {

		//
		String authEncoded = getAuthBase64(username,password);
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);

		Form form = new Form();
		form.add("Initiator", "xx");    
		form.add("SecurityCredential", "xx");
		form.add("CommandID", "xx");    
		form.add("PartyA", "xx");
		form.add("IdentifierType", "xx");    
		form.add("Remarks", "xx");
		form.add("QueueTimeOutURL", "xx");    
		form.add("ResultURL", "xx");

		ClientResponse response = webResource
				.accept("application/json")
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.header("Authorization", "Basic " + authEncoded)
				.post(ClientResponse.class, form);

		if(response.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}else {
			System.err.println("OK");
		}

		String output = response.getEntity(String.class);

		return output;
	}


	/**
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public static String initQuery(String url,String username,String password) {
		//
		String authEncoded = getAuthBase64(username,password);
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);
		ClientResponse resp = webResource.queryParam("grant_type", "BASIC")
				.accept("application/json")
				.header("Authorization", "Basic " + authEncoded)
				.get(ClientResponse.class);


		if(resp.getStatus() != 200){
			System.err.println("Unable to connect to the server");
		}

		String output = resp.getEntity(String.class);
		return output;
	}


	/**
	 * 
	 * @param username
	 * @param password
	 * @return
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

}
