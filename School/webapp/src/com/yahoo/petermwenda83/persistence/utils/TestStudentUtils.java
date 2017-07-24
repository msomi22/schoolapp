/**
 * 
 */
package com.yahoo.petermwenda83.persistence.utils;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author peter
 *
 */
public class TestStudentUtils {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String ACC_UUID ="B643876D-B90C-435C-B215-CC558D596626";
	
	private StudentUtils store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.utils.StudentUtils#getStudents(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetStudents() {
		store = new StudentUtils(databaseName, Host, databaseUsername, databasePassword, databasePort);
		equals(store.getStudents(ACC_UUID));
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.utils.StudentUtils#getIncomingCount(java.lang.String)}.
	 */
	//@Ignore
	@Test
	public void testGetIncomingCount() {
		store = new StudentUtils(databaseName, Host, databaseUsername, databasePassword, databasePort);
		equals(store.getIncomingCount(ACC_UUID));
	}

}
