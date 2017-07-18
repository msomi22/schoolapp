/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.staff.Duties;

/**
 * @author peter
 *
 */
public class TestDutiesDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String UUID = "C967E7CE-4F4F-4B0A-9812-0003DF5847EA";
	final String SCHOOL = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	final String DUTY = "HOD Sciences";
	
	private DutiesDAO store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.DutiesDAO#getDuty(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetDuty() {
		 store = new DutiesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		 Duties duty = new Duties();
		 duty = store.getDuty(UUID);
		 assertEquals(duty.getUuid(),UUID);
		 assertEquals(duty.getSchoolAccountUuid(),SCHOOL);
		 assertEquals(duty.getDuty(),DUTY);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.DutiesDAO#putDuty(com.yahoo.petermwenda83.bean.staff.Duties)}.
	 */
	@Ignore
	@Test
	public void testPutDuty() {
		 store = new DutiesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.DutiesDAO#updateDuty(com.yahoo.petermwenda83.bean.staff.Duties)}.
	 */
	@Ignore
	@Test
	public void testUpdateDuty() {
		 store = new DutiesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.DutiesDAO#deleteDuty(com.yahoo.petermwenda83.bean.staff.Duties)}.
	 */
	@Ignore
	@Test
	public void testDeleteDuty() {
		 store = new DutiesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.DutiesDAO#getDutiesList()}.
	 */
	@Ignore
	@Test
	public void testGetDutiesList() {
		store = new DutiesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Duties> list = store.getDutiesList();
		for (Duties ss : list) {
			System.out.println(ss);
		}
	}

}
