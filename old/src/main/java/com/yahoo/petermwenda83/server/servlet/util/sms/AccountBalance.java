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
	
	public static String getBalance(String accountId) {
		
		String balance = "";
		
		if(accountDAO.getAccountById(accountId) == null) {
			return "Account not found!"; 
			
		}else if(apiCredentialDAO.getApiCredential(accountId, ApiConstants.SMS) == null) {
			return "API Credentials not found!"; 
			
		}else {
			
			ApiCredential smsApi = apiCredentialDAO.getApiCredential(accountId, ApiConstants.SMS);
			
			
			String username = smsApi.getApiPassword();
			String apiKey = smsApi.getApiKey();
			
			AfricasTalkingGateway gateway = new AfricasTalkingGateway(username, apiKey);
			
			try {
			       JSONObject result = gateway.getUserData();
			       balance = result.getString("balance");
			   }
			   
			   catch(Exception e){
			       System.out.println(e.getMessage());
			   }
			
			
		}
		
		return balance;
	}
	
}
