/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean.admin;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.yahoo.petermwenda83.bean.account.Account;

/**
 * @author peter
 *
 */
public class Test {

	/**
	 * 
	 */
	public Test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		ApiAccount apiAccount = new ApiAccount();
		apiAccount.setName("zzzzzzzzzzzzzzzz");
		
		Account account = new Account();
		account = apiAccount;
		
		System.out.println(account);
		
		
		
		if(apiAccount instanceof Account) {
			System.out.println("Good");
			
		}else {
			System.out.println("BAD");
		}
		
		


	}

}
