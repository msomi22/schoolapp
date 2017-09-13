/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.util.sms;

/**
 * @author peter
 *
 */
public class TestSend {

	/**
	 * 
	 */
	public TestSend() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		
		String accountId = "";
		String mobile = "";
		String message = "";
		String key = "";
		String secret = "";
		SmsObject smsObject = new SmsObject(accountId,mobile,message,key,secret);
		String description = SmsUtil.sendSMS(smsObject); 
		
		System.out.println(description); 
		
	}

}
