/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.util.sms;

/**
 * @author peter
 *
 */
public class SmsObject {
	
	private String account;
	private String mobile;
	private String message;
	private String apiUsername;
	private String apiKey;
	
	public SmsObject() {
		account = "";
		mobile = "";
		message = "";
		apiUsername = "";
		apiKey = "";
	}
	/**
	 * @param args
	 */
	public SmsObject(String ... args) {
		
		if(args.length == 5) {
			
			 account = args[0];
			 mobile = args[1];
			 message = args[2];
			 apiUsername = args[3];
			 apiKey = args[4]; 
			 
		}else {
			try {
				throw new InvalidParamException("Wrong number of parameters supplied!"); 
			} catch (InvalidParamException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 
	 */
	public SmsObject(String account,String mobile,String message,String apiUsername,String apiKey) {
		this.account = account;
		this.mobile = mobile;
		this.message = message;
		this.apiUsername = apiUsername;
		this.apiKey = apiKey;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getApiUsername() {
		return apiUsername;
	}

	public void setApiUsername(String apiUsername) {
		this.apiUsername = apiUsername;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String toString() {
		return "SmsObject [account=" + account + ", mobile=" + mobile + ", message=" + message + ", apiUsername="
				+ apiUsername + ", apiKey=" + apiKey + "]";
	}

	
}
