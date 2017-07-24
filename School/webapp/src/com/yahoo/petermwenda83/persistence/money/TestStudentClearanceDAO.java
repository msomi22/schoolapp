/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.money.StudentClearance;

/**
 * @author peter
 *
 */
public class TestStudentClearanceDAO {
	

	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	
	final String STUDDEENT_UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
	
	final double AMOUNT = 2000;
			
	final String TERM = "1";
			
	final String YEAR = "2016";
	
	
	private StudentClearanceDAO store;
	

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentClearanceDAO#StudentClearanceDAO(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Ignore
	@Test
	public final void testStudentClearanceDAOStringStringStringStringInt() {
		store = new StudentClearanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentClearanceDAO#getClearance(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetClearance() {
		store = new StudentClearanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentClearanceDAO#getClearanceByYear(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetClearanceByYear() {
		store = new StudentClearanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentClearanceDAO#getClearanceByStudentId(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetClearanceByStudentId() {
		store = new StudentClearanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentClearanceDAO#existStudent(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testExistStudent() {
		store = new StudentClearanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentClearanceDAO#put(com.yahoo.petermwenda83.bean.money.StudentClearance, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	//@Ignore
	@Test
	public final void testPut() {
		store = new StudentClearanceDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentClearance clearance = new StudentClearance();
		clearance.setStudentUuid(STUDDEENT_UUID);
		clearance.setClearingAmount(AMOUNT);
		clearance.setTerm(TERM);
		clearance.setYear(YEAR);
		store.put(clearance, STUDDEENT_UUID, TERM, YEAR);
	}

}
