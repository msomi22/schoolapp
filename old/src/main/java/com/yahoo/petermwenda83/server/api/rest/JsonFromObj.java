package com.yahoo.petermwenda83.server.api.rest;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

public class JsonFromObj {

	public JsonFromObj() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		
		Gson gson = new Gson();
		
		String jsonString = gson.toJson(new APIStaff());
	
		System.out.println(jsonString);
		
		System.out.println(StaffRestFulAPI.validMobileNo("h718953974"));  

	}

}
