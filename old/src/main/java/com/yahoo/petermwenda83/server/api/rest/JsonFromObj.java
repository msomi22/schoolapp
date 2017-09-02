package com.yahoo.petermwenda83.server.api.rest;

//import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiStaffFull;
import com.yahoo.petermwenda83.server.api.rest.bean.SubClass;
import com.yahoo.petermwenda83.server.api.safaricom.Result;
import com.yahoo.petermwenda83.server.api.safaricom.bean.SafResponse;
import com.yahoo.petermwenda83.server.api.safaricom.bean.VCResponse;

public class JsonFromObj {

	public JsonFromObj() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		System.out.println(getJsonStringFromObject(new VCResponse()));
		
		//System.out.println(StaffRestFulAPI.validMobileNo("h718953974"));  

	}
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	public static String getJsonStringFromObject(Object object) { 
		Gson gson = new Gson();
		return gson.toJson(object);
	}

}
