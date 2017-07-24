/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.money.Deposit;
import com.yahoo.petermwenda83.bean.money.TermFee;

/**
 * @author peter
 *
 */
public class TestTermFeeDAO {
	

	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String UUID = "CAFF8382-E601-4E51-89FB-9A5E892782D5",
			     UUID_NEW="46A9B381-7E7D-4626-A48C-1573C011D8AE";
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	
	final double TERM_FEE = 15000,
			     TERM_FEE_NEW = 0.0,
			     TERM_FEE_UPDATE = 0.0;
	
	private TermFeeDAO store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.TermFeeDAO#getTermFee(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetTermFee() {
		store = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		TermFee t = new TermFee();
		//t = store.getTermFee(SCHOOL_UUID);
		assertEquals(t.getUuid(),UUID);
		assertEquals(t.getSchoolAccountUuid(),SCHOOL_UUID);
		assertEquals(t.getTermAmount(),TERM_FEE,0);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.TermFeeDAO#putTermFee(com.yahoo.petermwenda83.bean.money.TermFee)}.
	 */
	@Ignore
	@Test
	public final void testPutTermFee() {
		store = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		TermFee t = new TermFee();
		t.setUuid(UUID_NEW);
		t.setSchoolAccountUuid(SCHOOL_UUID); 
		t.setTermAmount(TERM_FEE_NEW);
		//assertTrue(store.putTermFee(t));
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.TermFeeDAO#updateTermFee(com.yahoo.petermwenda83.bean.money.TermFee)}.
	 */
	
	@Ignore
	@Test
	public final void testUpdateTermFee() {
		store = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		TermFee t = new TermFee();
		t.setUuid(UUID_NEW);
		t.setSchoolAccountUuid(SCHOOL_UUID); 
		t.setTermAmount(TERM_FEE_UPDATE);
		///assertTrue(store.updateTermFee(t)); 
	}
	
	
	//@Ignore
	@Test
	public final void testgetTermFeeList() {
		store = new TermFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<TermFee> dlist = store.getTermFeeList(SCHOOL_UUID);	
		for (TermFee d : dlist) {
			System.out.println(d);	
		}
	}
}
