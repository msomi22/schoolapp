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

import org.json.*;
/**
 * @author peter
 *
 */

import ke.co.qubintel.school.server.api.ApiConstants;
import ke.co.qubintel.school.server.api.rest.bean.ApiResponse;
import ke.co.qubintel.school.server.bean.account.ApiCredential;
import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.persistence.schoolaccount.ApiCredentialDAO;
public class AccountBalance {
	
	private static AccountDAO accountDAO;
	private static ApiCredentialDAO apiCredentialDAO;
	
	static {
		accountDAO = AccountDAO.getInstance();
		apiCredentialDAO = ApiCredentialDAO.getInstance();
	}

	/**
	 * 
	 */
	
	public static Object getBalance(String accountId) {
		
		
		ApiResponse apiResponse = new ApiResponse("error"); 
		
		if(accountDAO.getAccountById(accountId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account not found!");
			return apiResponse;
			
		}else if(apiCredentialDAO.getApiCredential(accountId, ApiConstants.SMS) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("API Credentials not found!");
			return apiResponse;
			
		}else {
			
			ApiCredential smsApi = apiCredentialDAO.getApiCredential(accountId, ApiConstants.SMS);
			
			
			String username = smsApi.getApisecret();
			String apiKey = smsApi.getApiKey();
			
			AfricasTalkingGateway gateway = new AfricasTalkingGateway(username, apiKey);
			
			try {
				  String balance = "";
				
			       JSONObject result = gateway.getUserData();
			       balance = result.getString("balance");
			       apiResponse.setMessage("sucess"); 
				   apiResponse.setDescription(balance);
				   return apiResponse;
			   }
			   
			   catch(Exception e){
			       System.out.println(e.getMessage());
			   }
			
			
		}
		
		return apiResponse;
	}
	
}
