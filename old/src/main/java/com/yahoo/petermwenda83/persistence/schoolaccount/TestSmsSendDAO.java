/**
 * 
 *//*
package com.yahoo.petermwenda83.persistence.schoolaccount;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.account.OutGoingSMS;

*//**
 * @author peter
 *
 *//*
public class TestSmsSendDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private SmsSendDAO store;

	*//**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO#getSmsSend(java.lang.String)}.
	 *//*
	@Ignore
	@Test
	public final void testGetSmsSendString() {
		store = new SmsSendDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
	}

	*//**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO#getSmsSendByStatus(java.lang.String)}.
	 *//*
	@Ignore
	@Test
	public final void testGetSmsSendByStatus() {
		store = new SmsSendDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
	}

	*//**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO#putSmsSend(com.yahoo.petermwenda83.bean.account.OutGoingSMS)}.
	 *//*
	@Ignore
	@Test
	public final void testPutSmsSend() {
		store = new SmsSendDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
	}

	*//**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO#updateSmsSend(com.yahoo.petermwenda83.bean.account.OutGoingSMS)}.
	 *//*
	@Ignore
	@Test
	public final void testUpdateSmsSend() {
		store = new SmsSendDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
	}

	*//**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO#deleteSmsSend(com.yahoo.petermwenda83.bean.account.OutGoingSMS)}.
	 *//*
	@Ignore
	@Test
	public final void testDeleteSmsSend() {
		store = new SmsSendDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
	}

	*//**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO#getSmsSend()}.
	 *//*
	@Ignore
	@Test
	public final void testGetSmsSend() {
		store = new SmsSendDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
		List<OutGoingSMS> list = store.getSmsSendList("");
		for (OutGoingSMS ss : list) {
			System.out.println(ss);
		}
	}

}
*/