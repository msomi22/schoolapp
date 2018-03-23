/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.servlet.util.sms;

import org.apache.commons.lang3.StringUtils;
import org.json.*;

import ke.co.qubintel.school.server.bean.account.OutGoingSMS;
import ke.co.qubintel.school.server.persistence.account.OutGoingSMSDAO;

/**
 * @author peter
 *
 */
public class SmsUtil {
	
	private static OutGoingSMSDAO outGoingSMSDAO;
	
	static {
		outGoingSMSDAO = OutGoingSMSDAO.getInstance(); //TODO
		//outGoingSMSDAO = new OutGoingSMSDAO("schooldb", "localhost", "school", "AllaManO1", 5432); 
	}
	
	
	public static String sendSMS(SmsObject smsObject){
		
		String description = "";
		
		String mobile = smsObject.getMobile();
		
		if(!StringUtils.isNumeric(mobile)) {
			description = "Invalid number!";
			return description;
			
		}else if(mobile.length() != 9){  
			description = "Invalid number!";
			return description;
			
		}else {
			
			AfricasTalkingGateway gateway  = new AfricasTalkingGateway(smsObject.getApiUsername(), smsObject.getApiKey());
			
			try {
				
				mobile = "+254"+mobile; 
	            JSONArray results = gateway.sendMessage(mobile, smsObject.getMessage());
	            
	            for( int i = 0; i < results.length(); ++i ) {
	                  JSONObject result = results.getJSONObject(i);
	                  
	                  OutGoingSMS outGoingSMS = new OutGoingSMS();
	                  outGoingSMS.setAccountId(smsObject.getAccount()); 
	                  outGoingSMS.setMessage(smsObject.getMessage());
	                  outGoingSMS.setMobile(result.getString("number"));
	                  outGoingSMS.setStatus(result.getString("status"));
	                  outGoingSMS.setSmsCost(result.getString("cost")); 
	                  
	                  outGoingSMSDAO.putOutGoingSMS(outGoingSMS);
	                  
	                  System.out.println(outGoingSMS); 
	        
	                  description = "Message sent successfully.";
	                 
	        }
	       }
	       catch (Exception e) {
	    	   OutGoingSMS outGoingSMS = new OutGoingSMS();
               outGoingSMS.setAccountId(smsObject.getAccount()); 
               outGoingSMS.setMessage(smsObject.getMessage());
               outGoingSMS.setMobile(smsObject.getMobile());
               outGoingSMS.setStatus("api_auth_err");
               outGoingSMS.setSmsCost("");  
               outGoingSMSDAO.putOutGoingSMS(outGoingSMS);
               
	    	   description = "Encountered an error while sending " + e.getMessage();
	           System.out.println(description);
	        }
			
		}
		return description;
	}
	
	
}
