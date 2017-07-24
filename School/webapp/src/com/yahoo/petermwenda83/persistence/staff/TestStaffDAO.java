/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.staff.Staff;

/**
 * @author peter
 *
 */
public class TestStaffDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		         
	final String UUID = "708D2D79-BD0A-4338-BC75-62A5659A4F56",
			     UUID_NEW = "D976744A-C3FC-4AAC-8F89-BF21A79CC824";
	
	final String CATEGORY = "Teaching",
			     CATEGORY_NEW = "NEW",
			     CATEGORY_UPDATE = "UPDATE";
	
	final String POSITION_UUID = "C3915245-00EE-4EF4-9898-ACE59683DD60",
			     POSITION_UUID_NEW = "BDF7F33D-1936-43F3-B14B-8FC3EA3A1265";
	
	final String  USERNAME = "jane",
			      USERNAME_NEW = "NEW",
			      USERNAME_UPDATE = "UPDATE";
	
	final String PASSWORD = "fe01ce2a7fbac8fafaed7c982a04e229",
			     PASSWORD_NEW = "NEW",
			     PASSWORD_UPDATE = "UPDATE";
	
	private StaffDAO store;
	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDAO#getStaff(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetStaff() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Staff s = new Staff();
		s = store.getStaffByUsername(SCHOOL_UUID, USERNAME);
		assertEquals(s.getCategory(),CATEGORY);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDAO#getStaffByUsername(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetStaffByUsername() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Staff s = new Staff();
		s.setUuid(UUID);
		s.setSchoolAccountUuid(SCHOOL_UUID);
	}

	

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDAO#getStaffByUsername(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	//@Ignore
	@Test
	public void testGetStaffByPosition() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Staff s = new Staff();
		s = store.getStaffByPosition(SCHOOL_UUID, SCHOOL_UUID);
		assertEquals(s.getSchoolAccountUuid(),SCHOOL_UUID);
		//POSITION_UUID
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDAO#putStaff(com.yahoo.petermwenda83.bean.staff.Staff)}.
	 */
	@Ignore
	@Test
	public void testPutStaff() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Staff s = new Staff();
		s.setUuid(UUID_NEW);
		s.setSchoolAccountUuid(SCHOOL_UUID);
		s.setCategory(CATEGORY_NEW);
		s.setPositionUuid(POSITION_UUID_NEW);
		s.setUserName(USERNAME_NEW);
		s.setPassword(PASSWORD_NEW); 
		assertTrue(store.putStaff(s)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDAO#updateStaff(com.yahoo.petermwenda83.bean.staff.Staff)}.
	 */
	@Ignore
	@Test
	public void testUpdateStaff() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Staff s = new Staff();
		s.setUuid(UUID_NEW);
		s.setSchoolAccountUuid(SCHOOL_UUID);
		s.setCategory(CATEGORY_UPDATE);
		s.setPositionUuid(POSITION_UUID_NEW);
		s.setUserName(USERNAME_UPDATE);
		s.setPassword(PASSWORD_UPDATE); 
		assertTrue(store.updateStaff(s));  
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDAO#deleteStaff(com.yahoo.petermwenda83.bean.staff.Staff)}.
	 */
	@Ignore
	@Test
	public void testDeleteStaff() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDAO#getStaffList(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetStaffList() {
		store = new StaffDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Staff> list = store.getStaffList(SCHOOL_UUID);
		for (Staff ss : list) {
			System.out.println(ss);
		}
	}

}
