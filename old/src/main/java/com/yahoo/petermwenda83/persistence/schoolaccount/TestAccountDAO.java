/**
 * 
 */
package com.yahoo.petermwenda83.persistence.schoolaccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.account.Account;

/**
 * @author peter
 *
 */
public class TestAccountDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
	             SCHOOL_UUID_NEW ="HJKHG56-A0D8-4F27-B9D2-E329619DF055";
	
	final String STATUS = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
          

	private String SCHOOL_NAME ="Njuri High School",
			       SCHOOL_NAME_NEW="New ",
			       SCHOOL_NAME_UPDATE="update ";
	
	private String USERNAME ="njuri",
			       USERNAME_NEW ="New",
			       USERNAME_UPDATE ="Update";
	
	private String PASSWORD ="njuri",
			       PASSWORD_NEW ="new",
			       PASSWORD_UPDATE ="update";
	
	private String MOBILE ="254-718953974",
			       MOBILE_NEW ="77777777",
			       MOBILE_UPDATE ="999999999";
	
	private String EMAIL ="njuri@info.co.ke",
			       EMAIL_NEW ="new@yahoo.com",
			       EMAIL_UPDATE ="update@yahoo.com";
	

	
	private AccountDAO store;


	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO#getSchoolByUsername(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetSchoolByUsername() {
		store = new AccountDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Account s = new Account();
		
		assertEquals(s.getUuid(),SCHOOL_UUID);
		//assertEquals(s.getSchoolName(),SCHOOL_NAME);
		assertEquals(s.getUsername(),USERNAME);
		assertEquals(s.getPassword(),PASSWORD);
		assertEquals(s.getMobile(),MOBILE);
		assertEquals(s.getEmail(),EMAIL);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO#put(com.yahoo.petermwenda83.bean.account.Account)}.
	 */
	@Ignore
	@Test
	public void testPut() {
		store = new AccountDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Account s = new Account();
		s.setUuid(SCHOOL_UUID_NEW);
		//s.setStatusUuid(STATUS);
		//s.setSchoolName(SCHOOL_NAME_NEW);
		s.setUsername(USERNAME_NEW);
		s.setPassword(PASSWORD_NEW);
		s.setMobile(MOBILE_NEW);
		s.setEmail(EMAIL_NEW);
		assertTrue(store.putAccount(s));
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO#update(com.yahoo.petermwenda83.bean.account.Account)}.
	 */
	@Ignore
	@Test
	public void testUpdate() {
		store = new AccountDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Account s = new Account();
		s.setUuid(SCHOOL_UUID_NEW);
		//s.setSchoolName(SCHOOL_NAME_UPDATE);
		s.setUsername(USERNAME_UPDATE);
		s.setPassword(PASSWORD_UPDATE);
		s.setMobile(MOBILE_UPDATE);
		s.setEmail(EMAIL_UPDATE);
		assertTrue(store.updateAccount(s)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO#delete(com.yahoo.petermwenda83.bean.account.Account)}.
	 */
	@Ignore
	@Test
	public void testDelete() {
		store = new AccountDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		//Account s = new Account();
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO#getAllSchools()}.
	 */
	@Ignore
	@Test
	public void testGetAllSchools() {
		store = new AccountDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Account> list = store.getAccounts();	
		for (Account ss : list) {
			System.out.println(ss);
		}
		
	}

}
