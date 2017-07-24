/**
 * 
 */
package com.yahoo.petermwenda83.persistence.student;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.student.House;

/**
 * @author peter
 *
 */
public class TestHouseDAO {
	

	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	
	private HouseDAO store;
	
	final String UUID = "C69AEA6C-AFEF-4AD6-9779-0067C9C77A61",
			     UUID_NEW = "8F41AC10-CEDB-4404-BC32-138DD149F117";
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	
	final String HOUSE_NAME = "Suswa",
			     HOUSE_NAME_NEW = "new",
			     HOUSE_NAME_UPDATE = "update";

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.HouseDAO#getHouse(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetHouse() {
		store = new HouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		House h = new House();
		h = store.getHouse(SCHOOL_UUID, UUID);
		assertEquals(h.getUuid(),UUID);
		assertEquals(h.getSchoolAccountUuid(),SCHOOL_UUID);
		assertEquals(h.getHouseName(),HOUSE_NAME);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.HouseDAO#putHouse(com.yahoo.petermwenda83.bean.student.House)}.
	 */
	@Ignore
	@Test
	public void testPutHouse() {
		store = new HouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		House h = new House();
		h.setUuid(UUID_NEW);
		h.setSchoolAccountUuid(SCHOOL_UUID);
		h.setHouseName(HOUSE_NAME_NEW);
		assertTrue(store.putHouse(h)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.HouseDAO#updateHouse(com.yahoo.petermwenda83.bean.student.House)}.
	 */
	@Ignore
	@Test
	public void testUpdateHouse() {
		store = new HouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		House h = new House();
		h.setUuid(UUID_NEW);
		h.setSchoolAccountUuid(SCHOOL_UUID);
		h.setHouseName(HOUSE_NAME_UPDATE);
		assertTrue(store.updateHouse(h));  
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.HouseDAO#deleteHouse(com.yahoo.petermwenda83.bean.student.House)}.
	 */
	@Ignore
	@Test
	public void testDeleteHouse() {
		store = new HouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		House h = new House();
		h.setUuid(UUID_NEW);
		h.setSchoolAccountUuid(SCHOOL_UUID);
		assertTrue(store.deleteHouse(h));  
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.HouseDAO#getHouseList(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetHouseList() {
		store = new HouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<House> list = store.getHouseList(SCHOOL_UUID);
		for (House h : list) {
			System.out.println(h);	
		}
	}

}
