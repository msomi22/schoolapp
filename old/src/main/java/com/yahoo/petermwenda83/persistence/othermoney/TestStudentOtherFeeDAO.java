/**
 * 
 */
package com.yahoo.petermwenda83.persistence.othermoney;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author peter
 *
 */
public class TestStudentOtherFeeDAO {
	
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private StudentOtherFeeDAO storable = new StudentOtherFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO#StudentOtherFeeDAO(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Ignore 
	@Test
	public void testStudentOtherFeeDAOStringStringStringStringInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO#getStudentOtherFee(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore 
	@Test
	public void testGetStudentOtherFee() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO#putStudentOtherFee(com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee)}.
	 */
	@Ignore 
	@Test
	public void testPutStudentOtherFee() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO#updateStudentOtherFee(com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee)}.
	 */
	@Test
	public void testUpdateStudentOtherFee() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO#getStudentOtherFeeList(java.lang.String, java.lang.String, int, int)}.
	 */
	@Ignore 
	@Test
	public void testGetStudentOtherFeeListStringStringIntInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO#getStudentOtherFeeList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	//@Ignore 
	@Test
	public void testGetStudentOtherFeeListStringStringStringString() {
		
		String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		String studentId = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
		String term = "1";
		long year = 2017;
		
		System.out.println(storable.getStudentOtherFeeList(accountId, studentId, term, year));
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO#getStudentOtherFeeList(java.lang.String, java.lang.String)}.
	 */
	@Ignore 
	@Test
	public void testGetStudentOtherFeeListStringString() {
		fail("Not yet implemented");
	}

}
