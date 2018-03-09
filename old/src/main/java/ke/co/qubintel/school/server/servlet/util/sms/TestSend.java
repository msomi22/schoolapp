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
		
		String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		String mobile = "718953974";
		String message = "test";
		String key = "768ad07782db6c44cc82c93ac0341ad70a7c6862b301c534074dd34d71ad8baf";
		String secret = "msomi22";
		SmsObject smsObject = new SmsObject(accountId,mobile,message,secret,key);
		SmsUtil.sendSMS(smsObject); 
		
		System.out.println(smsObject); 
		
		
		
	}

}
