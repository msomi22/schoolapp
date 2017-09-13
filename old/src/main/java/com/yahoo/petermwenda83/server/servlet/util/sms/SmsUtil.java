/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.util.sms;

import org.apache.commons.lang3.StringUtils;
import org.json.*;

import com.yahoo.petermwenda83.bean.account.OutGoingSMS;
import com.yahoo.petermwenda83.persistence.schoolaccount.OutGoingSMSDAO;

/**
 * @author peter
 *
 */
public class SmsUtil {
	
	private static OutGoingSMSDAO outGoingSMSDAO;
	
	static {
		outGoingSMSDAO = OutGoingSMSDAO.getInstance();
	}
	
	
	public static void sendSMS(SmsObject smsObject){
		
		String mobile = smsObject.getMobile();
		
		if(!StringUtils.isNumeric(mobile)) {
			//invalid number
			 System.out.println("invalid number!");
		}else if(mobile.length() != 9){  
			//invalid number
			 System.out.println("invalid number!");
		}else {
			
			AfricasTalkingGateway gateway  = new AfricasTalkingGateway(smsObject.getApiUsername(), smsObject.getApiKey());
			
			try {
				
				mobile = "+254"+mobile; 
	            JSONArray results = gateway.sendMessage(mobile, smsObject.getMessage());
	            
	            for( int i = 0; i < results.length(); ++i ) {
	                  JSONObject result = results.getJSONObject(i);
	                  
	                  OutGoingSMS outGoingSMS = new OutGoingSMS();
	                  outGoingSMS.setAccountId(smsObject.getAccount()); 
	                  outGoingSMS.setMessage(result.getString("messageId"));
	                  outGoingSMS.setMobile(result.getString("number"));
	                  outGoingSMS.setStatus(result.getString("status"));
	                  outGoingSMS.setSmsCost(result.getString("cost")); 
	                  outGoingSMSDAO.putOutGoingSMS(outGoingSMS);
	                 
	        }
	       }
	       catch (Exception e) {
	           System.out.println("Encountered an error while sending " + e.getMessage());
	        }
			
		}
	}
	
	
}
