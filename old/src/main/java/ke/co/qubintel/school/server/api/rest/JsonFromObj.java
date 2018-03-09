package ke.co.qubintel.school.server.api.rest;

//import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import ke.co.qubintel.school.server.api.rest.bean.SmsExams;

public class JsonFromObj {

	public JsonFromObj() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		System.out.println(getJsonStringFromObject(new SmsExams()));
		
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
