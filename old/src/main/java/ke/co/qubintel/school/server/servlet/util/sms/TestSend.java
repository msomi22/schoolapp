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
		//Basic Y29tUGxleDpyZVN0KkAhQXBp
	}
	
	public static void main(String[] args) {
		
		String accountId = "22bf25b1-23f4-4ed0-a9f4-46a5d7f7d65d";
		String mobile = "718953974";
		String message = "test";
		String key = "e052e0296b5f1e828401c12308f05ae9d85959ab6f0eae50ca6880da31beb693";
		String secret = "msomi22";
		SmsObject smsObject = new SmsObject(accountId,mobile,message,secret,key);
		SmsUtil.sendSMS(smsObject); 
		
		System.out.println(smsObject); 
		
		
		
	}

}




