/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.money.StudentFee;

/**
 * @author peter
 *
 */
public class TestStudentFeeDAO {
	

	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private StudentFeeDAO store;
	
	final String UUID = "0BC077F0-8DF9-46BC-9E14-5F4596598A97",
			     UUID_NEW ="27F9B6F9-FBB1-4184-94BB-BBFBD5C03353";
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	
	final String STUDENT_UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F",
		     STUDENTUUID_NEW ="A195BAF6-D6E7-43A5-B7C9-D6C627A42815";


	final String TRANSCATION_ID = "7B59B315-70B7-4470-BC62-9284B5AADE86",
      			 TRANSCATION_ID_NEW ="28519E33-63C9-4BEA-98B4-7D5DC4CCAE57",
      			 TRANSCATION_ID_UPDATE ="427E2D6D-39DF-4D01-A64E-A36E877058E5";
	
	final double AMOUNT_PAID = 6700,
			     AMOUNT_PAID_NEW =1111,
			     AMOUNT_PAID_UPDATE =10000;
	
	final String SYSTEM_USER = "Peter",
			     SYSTEM_USER_NEW ="new",
			     SYSTEM_USER_UPDATE ="update";
	
	final String TERM = "1";
	final String YEAR = "2014";
	
	
	final Date DATE_PAID =  new Date();
	

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentFeeDAO#getStudentFeeByStudentUuid(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentFeeByStudentUuid() {
		store = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentFee sf = new StudentFee();
		sf = store.getStudentFeeByStudentUuid(SCHOOL_UUID, STUDENT_UUID,TERM,YEAR);
		assertEquals(sf.getUuid(),UUID);
		assertEquals(sf.getSchoolAccountUuid(),SCHOOL_UUID);
		assertEquals(sf.getStudentUuid(),STUDENT_UUID);
		assertEquals(sf.getTransactionID(),TRANSCATION_ID);
		assertEquals(sf.getAmountPaid(),AMOUNT_PAID,0);
		assertEquals(sf.getSystemUser(),SYSTEM_USER);
		//assertEquals(sf.getDatePaid(),DATE_PAID);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentFeeDAO#getStudentFeeByTransactionId(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentFeeByTransactionId() {
		store = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentFee sf = new StudentFee();
		sf = store.getStudentFeeByTransactionId(SCHOOL_UUID, TRANSCATION_ID);
		assertEquals(sf.getUuid(),UUID);
		assertEquals(sf.getSchoolAccountUuid(),SCHOOL_UUID);
		assertEquals(sf.getStudentUuid(),STUDENT_UUID);
		assertEquals(sf.getTransactionID(),TRANSCATION_ID);
		assertEquals(sf.getAmountPaid(),AMOUNT_PAID,0);
		assertEquals(sf.getSystemUser(),SYSTEM_USER);
		//assertEquals(sf.getDatePaid(),DATE_PAID);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentFeeDAO#putStudentFee(com.yahoo.petermwenda83.bean.money.StudentFee)}.
	 */
	@Ignore
	@Test
	public final void testPutStudentFee() {
		store = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentFee sf = new StudentFee();
		//sf.setUuid(UUID_NEW);
		sf.setSchoolAccountUuid(SCHOOL_UUID);
		sf.setStudentUuid(STUDENTUUID_NEW);
		sf.setTransactionID(TRANSCATION_ID_NEW);
		sf.setAmountPaid(AMOUNT_PAID_NEW);
		sf.setDatePaid(DATE_PAID);
		sf.setTerm(TERM);
		sf.setYear(YEAR);
		sf.setSystemUser(SYSTEM_USER_NEW);
		assertTrue(store.putStudentFee(sf)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentFeeDAO#updateStudentFee(com.yahoo.petermwenda83.bean.money.StudentFee)}.
	 */
	@Ignore
	@Test
	public final void testUpdateStudentFee() {
		store = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentFee sf = new StudentFee();
		//sf.setUuid(UUID_NEW);
		sf.setSchoolAccountUuid(SCHOOL_UUID);
		sf.setStudentUuid(STUDENTUUID_NEW);
		sf.setTransactionID(TRANSCATION_ID_NEW);
		sf.setAmountPaid(AMOUNT_PAID_UPDATE);
		sf.setDatePaid(DATE_PAID);
		sf.setTerm(TERM);
		sf.setYear(YEAR);
		sf.setSystemUser(SYSTEM_USER_UPDATE);
		assertTrue(store.updateStudentFee(sf)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentFeeDAO#deleteStudentFee(com.yahoo.petermwenda83.bean.money.StudentFee)}.
	 */
	@Ignore
	@Test
	public final void testDeleteStudentFee() {
		store = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentFee sf = new StudentFee();
		sf.setSchoolAccountUuid(SCHOOL_UUID);
		sf.setStudentUuid(STUDENTUUID_NEW);
		assertTrue(store.deleteStudentFee(sf));
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentFeeDAO#getStudentFeeList(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentFeeList() {
		store = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentFee> list = store.getStudentFeeList(SCHOOL_UUID,TERM,YEAR);
		for (StudentFee l : list) {
					System.out.println(l);	
				}
			
	}
	
	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentFeeDAO#getStudentFeeList(java.lang.String)}.
	 */
	//@Ignore
	@Test
	public final void testGetStudentFeeByStudentUuidList() {
		store = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentFee> list = store.getStudentFeeByStudentUuidList(SCHOOL_UUID,STUDENT_UUID,TERM,YEAR);
		for (StudentFee l : list) {
					System.out.println(l);	
				}
			
	}
	
	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.money.StudentFeeDAO#getStudentFeeList(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetStudentFeeDistinctList() {
		store = new StudentFeeDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentFee> list = store.getStudentFeeDistinctList(SCHOOL_UUID,TERM,YEAR);
		for (StudentFee l : list) {
					System.out.println(l);	
				}
			
	}

}
