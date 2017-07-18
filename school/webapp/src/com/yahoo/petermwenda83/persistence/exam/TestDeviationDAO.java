/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.exam.Deviation;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;

/**
 * @author peter
 *
 */
public class TestDeviationDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	public DeviationDAO  store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.DeviationDAO#getDev(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetDev() {
		store = new DeviationDAO (databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.DeviationDAO#DevExist(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testDevExist() {
		store = new DeviationDAO (databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.DeviationDAO#putDev(com.yahoo.petermwenda83.bean.exam.Deviation)}.
	 */
	//@Ignore
	@Test
	public final void testPutDev() {
		store = new DeviationDAO (databaseName, Host, databaseUsername, databasePassword, databasePort);
		Deviation dev = store.getDev("d80285c3-ba17-49e6-888c-785627496266", "2016");
		dev.setStudentUuid("d80285c3-ba17-49e6-888c-785627496266");
		dev.setYear("2016");
		dev.setDevOne(10);
		dev.setDevTwo(3);
		dev.setDevThree(4); 
		//assertTrue(store.putDev(dev));
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.DeviationDAO#getDeviationList(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetDeviationList() {
		store = new DeviationDAO (databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Deviation> list = store.getDeviationList("2016");
		for (Deviation d : list) {
			System.out.println(d);
		}
	}

}
