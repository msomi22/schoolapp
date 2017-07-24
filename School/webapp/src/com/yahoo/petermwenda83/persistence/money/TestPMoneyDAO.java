/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.money.Deposit;
import com.yahoo.petermwenda83.bean.money.PocketMoney;
import com.yahoo.petermwenda83.bean.money.Withdraw;

/**
 * @author peter
 *
 */
public class TestPMoneyDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private PMoneyDAO store;
	
	final String UUID = "2F653FE2-4C0A-489F-B4D0-0D5B48BC8E71",
			     DUUID = "F3E4183D-2D38-4495-8582-B6E0DB7F0F40",
			     WUUID = "2F4B4A0B-69D9-4BBF-8F0B-B2F38CA7E9F3",
			     
			     UUID_NEW = "3A4265FC-4268-4B7F-9475-A614C1E089CD";
	
    final String STUDENT_UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F",
    		     STUDENT_NEW = "A195BAF6-D6E7-43A5-B7C9-D6C627A42815";
    
    final String WSTUDENT_UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F",
		         WSTUDENT_NEW = "A195BAF6-D6E7-43A5-B7C9-D6C627A42815";
    
    final String DSTUDENT_UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F",
		         DSTUDENT_NEW = "A195BAF6-D6E7-43A5-B7C9-D6C627A42815";
    
    final double AMOUNT = 3000,
    		     AMOUNT_NEW = 10,
    		     AMOUNT_UPDATE = 20;
    
    final double DAMOUNT = 5000,
		         DAMOUNT_NEW = 30,
		         DAMOUNT_UPDATE = 40;
    
    final double WAMOUNT = 800,
		         WAMOUNT_NEW = 50,
		         WAMOUNT_UPDATE = 60;
    
    
    final String DSYSTEM_USER = "Peter",
		         DSYSTEM_USER_NEW = "dnew",
		         DSYSTEM_USER_UPDATE = "dupdate";
    
    final String WSYSTEM_USER = "Peter",
		         WSYSTEM_USER_NEW = "wnew",
		         WSYSTEM_USER_UPDATE = "wupdate";
    
    final String TERM = "1";
    final String YEAR = "2014";
    
    final Date COMMITDATE = new Date();

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.PMoneyDAO#getMoney(java.lang.String)}.
	 */
   @Ignore
	@Test
	public void testGetMoney() {
		store = new PMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		PocketMoney pm = new PocketMoney();
		pm = store.getMoney(STUDENT_UUID);
		assertEquals(pm.getUuid(),UUID);
		assertEquals(pm.getStudentUuid(),STUDENT_UUID);
		assertEquals(pm.getAmount(),AMOUNT,0);
	}

    
	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.PMoneyDAO#studentExist(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testStudentExist() {
		store = new PMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		PocketMoney pm = new PocketMoney();
		pm.setStudentUuid(STUDENT_UUID);
		assertTrue(store.studentExist(STUDENT_UUID));
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.PMoneyDAO#hasBalance(com.yahoo.petermwenda83.bean.money.PocketMoney, double)}.
	 */
	@Ignore
	@Test
	public void testHasBalance() {
		store = new PMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		PocketMoney pm = new PocketMoney();
		pm.setStudentUuid(STUDENT_UUID);
		assertTrue(store.hasBalance(pm, 30)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.PMoneyDAO#addBalance(com.yahoo.petermwenda83.bean.money.PocketMoney, double)}.
	 */
    @Ignore
	@Test
	public void testAddBalance() {
		store = new PMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Deposit d = new Deposit();
		//d.setUuid(UUID_NEW);
		d.setStudentUuid(DSTUDENT_NEW);
		d.setAmount(DAMOUNT_UPDATE);
		d.setSystemUser(DSYSTEM_USER_UPDATE);
		d.setDateCommitted(COMMITDATE);
		assertTrue(store.addBalance(d, DAMOUNT_NEW)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.PMoneyDAO#deductBalance(com.yahoo.petermwenda83.bean.money.PocketMoney, double)}.
	 */
	@Ignore
	@Test
	public void testDeductBalance() {
		store = new PMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Withdraw w = new Withdraw();
		//w.setUuid(UUID_NEW);
		w.setStudentUuid(WSTUDENT_NEW);
		w.setAmount(WAMOUNT_UPDATE);
		w.setSystemUser(WSYSTEM_USER_UPDATE);
		w.setDateCommitted(COMMITDATE);
		assertTrue(store.deductBalance(w, WAMOUNT_NEW));  
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.PMoneyDAO#getWithdrawList(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetWithdrawList() {
		store = new PMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Withdraw> wlist = store.getWithdrawList(STUDENT_UUID,TERM,YEAR);
		for (Withdraw w : wlist) {
			System.out.println(w);	
		}
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.PMoneyDAO#getDepositList(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetDepositList() {
		store = new PMoneyDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Deposit> dlist = store.getDepositList(STUDENT_UUID,TERM,YEAR);	
		for (Deposit d : dlist) {
			System.out.println(d);	
		}
	}

}
