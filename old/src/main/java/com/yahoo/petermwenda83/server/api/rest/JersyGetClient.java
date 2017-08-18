package com.yahoo.petermwenda83.server.api.rest;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JersyGetClient {
	
	public JersyGetClient(){
		
	}
	
	public static void main(String[] args){
		
		String url = "http://localhost:8080/school/webapi/student/E3CDC578-37BA-4CDB-B150-DAB0409270CD/4DA86139-6A72-4089-8858-6A3A613FDFE6";
        String name = "demo";
        String password = "12345678";
        String auth = name + ":" + password;
        
        String authEncoded = "";
        
        try {
        	
			  authEncoded = Base64.getEncoder().encodeToString(auth.getBytes("utf-8")); 
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        
        System.out.println("Base64 encoded auth string: " + authEncoded);
        
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("application/json")
                                         .header("Authorization", "Basic " + authEncoded)
                                         .get(ClientResponse.class);
        if(resp.getStatus() != 200){
            System.err.println("Unable to connect to the server");
        }
        String output = resp.getEntity(String.class);
        System.out.println("response: "+output);
        
	}

}
