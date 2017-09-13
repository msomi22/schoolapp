/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.util.sms;

import org.json.*;
/**
 * @author peter
 *
 */

import com.yahoo.petermwenda83.bean.account.ApiCredential;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.ApiCredentialDAO;
import com.yahoo.petermwenda83.server.api.ApiConstants;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
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
