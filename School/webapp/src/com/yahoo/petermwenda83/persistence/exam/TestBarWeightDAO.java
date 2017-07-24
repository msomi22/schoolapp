/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.exam.BarWeight;

/**
 * @author peter
 *
 */
public class TestBarWeightDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String UUID = "BD95C78D-FE0A-4004-A63A-FE28C6EB7DBC";
	final String UUID_NEW = "AF95C78D-FE0A-4004-A63A-FE28C6EB7DHG";
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	
	final String STUDENT_UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F",
			     STUDENT_UUID_NEW = "A195BAF6-D6E7-43A5-B7C9-D6C627A42815";
	
	final String TERM = "1",
			     TERM_NEW = "2",
			     TERM_UPDATE = "3";
	
	final String YEAR = "2016",
			     YEAR_NEW = "2017",
			     YEAR_UPDATE = "2018";
	
	final double WEIGHT_ONE = 6,
			     WEIGHT_ONE_NEW = 5,
			     WEIGHT_ONE_UPDATE = 3;
	
	final double WEIGHT_TWO = 7,
			     WEIGHT_TWO_NEW = 5,
			     WEIGHT_TWO_UPDATE = 6;
	
	final double WEIGHT_THREE = 4,
			     WEIGHT_THREE_NEW = 8,
			     WEIGHT_THREE_UPDATE = 9;
	
	
	
	
	private BarWeightDAO store;
	

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.BarWeightDAO#getBarWeight(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetBarWeight() {
		store = new BarWeightDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		BarWeight weight = new BarWeight();
		weight = store.getBarWeight(SCHOOL_UUID, STUDENT_UUID,YEAR);
		assertEquals(weight.getUuid(),UUID);
		assertEquals(weight.getSchoolAccountUuid(),SCHOOL_UUID);
		assertEquals(weight.getStudentUuid(),STUDENT_UUID);
		assertEquals(weight.getTerm(),TERM);
		assertEquals(weight.getYear(),YEAR);
		assertEquals(weight.getWeightOne(),WEIGHT_ONE,0);
		assertEquals(weight.getWeightTwo(),WEIGHT_TWO,0);
		assertEquals(weight.getWeightThree(),WEIGHT_THREE,0);
		
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.BarWeightDAO#put(com.yahoo.petermwenda83.bean.exam.BarWeight)}.
	 */
	//@Ignore
	@Test
	public final void testPut() {
		store = new BarWeightDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		BarWeight weight = new BarWeight();
		//weight.setUuid(UUID_NEW);
		weight.setSchoolAccountUuid(SCHOOL_UUID);
		weight.setStudentUuid(STUDENT_UUID_NEW);
		weight.setYear("2018");
		weight.setWeightOne(5);
		weight.setWeightTwo(8);
		weight.setWeightThree(10);
		//assertTrue(store.put(weight)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.BarWeightDAO#update(com.yahoo.petermwenda83.bean.exam.BarWeight)}.
	 */
	@Ignore
	@Test
	public final void testUpdate() {
		store = new BarWeightDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		BarWeight weight = new BarWeight();
		weight.setUuid(UUID_NEW);
		weight.setSchoolAccountUuid(SCHOOL_UUID);
		weight.setStudentUuid(STUDENT_UUID_NEW);
		weight.setTerm(TERM_NEW);
		weight.setYear(YEAR_NEW);
		weight.setWeightOne(WEIGHT_ONE_UPDATE);
		weight.setWeightTwo(WEIGHT_TWO_UPDATE);
		weight.setWeightThree(WEIGHT_ONE_UPDATE);
		assertTrue(store.update(weight));  
	}

}
