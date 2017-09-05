/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.google.gson.Gson;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.admin.ApiAccount;

/**
 * @author peter
 *
 */
public class AdminService {

	private static AccountDAO accountDAO;
	private static EmailValidator emailValidator;

	static {
		accountDAO = AccountDAO.getInstance();
		emailValidator = EmailValidator.getInstance();
	}


	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Object getAccountById(String uuid) {
		
		ApiResponse apiResponse = new ApiResponse(); 

		if(accountDAO.getAccountById(uuid) == null) {
			
			apiResponse = new ApiResponse("error");
			apiResponse.setDescription("Account not found!"); 
			return apiResponse;

		}

		return accountDAO.getAccountById(uuid);
	}

	/**
	 * 
	 * @return
	 */
	public List<ApiAccount> getAccounts() {

		Gson gson = new Gson();

		List<ApiAccount> accounts = new ArrayList<>();

		accountDAO.getAccounts().forEach(account -> {

			String tmpAccount =  gson.toJson(account);
			ApiAccount apiAccount = gson.fromJson(tmpAccount, ApiAccount.class);
			accounts.add(apiAccount); 

		});

		return accounts;
	}
	
	
	/**
	 * 
	 * @param apiAccount
	 * @return
	 */
	public Object newAccount(ApiAccount apiAccount) {
		
		ApiResponse apiResponse = new ApiResponse(); 
		
		
		if(StringUtils.isBlank(apiAccount.getName()) && apiAccount.getName().length() < 5) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School name.");
			return apiResponse;
			
		}else if(accountDAO.getAccount(apiAccount.getName(), "1") !=null) { 
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school name is in use.");
			return apiResponse;
			
		}else if(StringUtils.isBlank(apiAccount.getMotto()) && apiAccount.getMotto().length() < 5) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School motto.");
			return apiResponse;
			
		}else if(StringUtils.isBlank(apiAccount.getUsername()) && apiAccount.getUsername().length() < 3) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School username.");
			return apiResponse;
			
		}else if(accountDAO.getAccount(apiAccount.getUsername(), "1") !=null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school username is in use.");
			return apiResponse;
			
		}else if(StringUtils.isBlank(apiAccount.getPassword())&& apiAccount.getPassword().length() < 4) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School password.");
			return apiResponse;
			
		}else if(StringUtils.isBlank(apiAccount.getMobile()) && !StringUtils.isNumeric(apiAccount.getMobile()) && 
				apiAccount.getMobile().length() !=9) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School mobile number.");
			return apiResponse;
			
		}else if(accountDAO.getAccount(apiAccount.getMobile(), "1") !=null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school mobile is in use.");
			return apiResponse;
			
		}else if(StringUtils.isBlank(apiAccount.getEmail()) && !emailValidator.isValid(apiAccount.getEmail())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School email address.");
			return apiResponse;
			
		}else if(accountDAO.getAccount(apiAccount.getEmail(), "1") !=null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("The school email is in use.");
			return apiResponse;
			
		}else if(StringUtils.isBlank(apiAccount.getAddress()) && apiAccount.getAddress().length() < 2) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid School postal address.");
			return apiResponse;
			
		}else if(StringUtils.isBlank(apiAccount.getTown()) && apiAccount.getTown().length() < 3) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid town.");
			return apiResponse;
			
		}else if(StringUtils.isBlank(apiAccount.getIsBoarding())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid IsBoarding!.");
			return apiResponse;
			
		}else if(StringUtils.isBlank(apiAccount.getIsMixed())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid IsMixed!.");
			return apiResponse;
			
		}else {
			
			

			Account account = new Account();
			account = apiAccount;
			
			if(accountDAO.putAccount(account)) {
				apiResponse.setMessage("success");
				apiResponse.setDescription("Account registered successfully");
				
			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Something went wrong while registering the account.");
			}
			
		}
		
		return apiResponse;
		
	}
	
	/**
	 * 
	 * @param apiAccount
	 * @return
	 */
	public Object updateAccount(ApiAccount apiAccount) {
		ApiResponse apiResponse = new ApiResponse();
		
		
		
		
		return apiResponse;
	}



}
