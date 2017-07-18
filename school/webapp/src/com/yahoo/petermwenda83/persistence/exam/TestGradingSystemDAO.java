/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.exam.GradingSystem;

/**
 * @author peter
 *
 */
public class TestGradingSystemDAO {
	

	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private GradingSystemDAO store;
	
    final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	
	   final String UUID = "1EABC062-DC76-42FC-A817-52D89C8CDAE9",
			     UUID_NEW = "2F0BD8E1-32F9-4A75-A77B-C3D991A09DC4";
	
	   final int GRADE_A = 100,
			     GRADE_A_NEW = 2,
			     GRADE_A_UPDATE = 3;
	
	   final int GRADE_A_MINUS = 80,
			     GRADE_A_MINUS_NEW = 2,
			     GRADE_A_MINUS_UPDATE = 3;
	
	   final int GRADE_B_PLUS = 70,
			     GRADE_B_PLUS_NEW = 2,
			     GRADE_B_PLUS_UPDATE = 3;
	
	   final int GRADE_B_PLAIN = 65,
			     GRADE_B_PLAIN_NEW = 2,
			     GRADE_B_PLAIN_UPDATE = 3;
	
	   final int GRADE_B_MINUS = 60,
			     GRADE_B_MINUS_NEW = 2,
			     GRADE_B_MINUS_UPDATE = 3;
	
	   final int GRADE_C_PLUS = 55,
			     GRADE_C_PLUS_NEW = 2,
			     GRADE_C_PLUS_UPDATE = 3;
	
	   final int GRADE_C_PLAIN = 50,
			     GRADE_C_PLAIN_NEW = 2,
			     GRADE_C_PLAIN_UPDATE = 3;
	
	   final int GRADE_C_MINUS = 45,
			     GRADE_C_MINUS_NEW = 2,
			     GRADE_C_MINUS_UPDATE = 3;
	
	   final int GRADE_D_PLUS = 40,
			     GRADE_D_PLUS_NEW = 2,
			     GRADE_D_PLUS_UPDATE = 3;
	
	   final int GRADE_D_PLAIN = 35,
			     GRADE_D_PLAIN_NEW = 2,
			     GRADE_D_PLAIN_UPDATE = 3;
	
	   final int GRADE_D_MINUS = 30,
			     GRADE_D_MINUS_NEW = 2,
			     GRADE_D_MINUS_UPDATE = 3;
	
	   final int GRADE_E = 0,
			     GRADE_E_NEW = 2,
			     GRADE_E_UPDATE = 3;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO#getGradingSystem(java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetGradingSystem() {
		store = new GradingSystemDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		GradingSystem g = new GradingSystem();
		g = store.getGradingSystem(SCHOOL_UUID);
		assertEquals(g.getGradeAplain(),GRADE_A);
		assertEquals(g.getGradeAminus(),GRADE_A_MINUS);
		assertEquals(g.getGradeBplus(),GRADE_B_PLUS);
		assertEquals(g.getGradeBplain(),GRADE_B_PLAIN);
		assertEquals(g.getGradeBminus(),GRADE_B_MINUS);
		assertEquals(g.getGradeCplus(),GRADE_C_PLUS);
		assertEquals(g.getGradeCplain(),GRADE_C_PLAIN);
		assertEquals(g.getGradeCminus(),GRADE_C_MINUS);
		assertEquals(g.getGradeDplus(),GRADE_D_PLUS);
		assertEquals(g.getGradeDplain(),GRADE_D_PLAIN);
		assertEquals(g.getGradeDminus(),GRADE_D_MINUS);
		assertEquals(g.getGradeE(),GRADE_E);
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO#putGradingSystem(com.yahoo.petermwenda83.bean.exam.GradingSystem)}.
	 */
	@Ignore
	@Test
	public final void testPutGradingSystem() {
		store = new GradingSystemDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		GradingSystem g = new GradingSystem();
		g.setUuid(UUID_NEW);
		g.setSchoolAccountUuid(SCHOOL_UUID);
		g.setGradeAplain(GRADE_A_NEW);
		g.setGradeAminus(GRADE_A_MINUS_NEW);
		g.setGradeBplus(GRADE_B_PLUS_NEW);
		g.setGradeBplain(GRADE_B_PLAIN_NEW);
		g.setGradeBminus(GRADE_B_MINUS_NEW);
		g.setGradeCplus(GRADE_C_PLUS_NEW);
		g.setGradeCplain(GRADE_C_PLAIN_NEW);
		g.setGradeCminus(GRADE_C_MINUS_NEW);
		g.setGradeDplus(GRADE_D_PLUS_NEW);
		g.setGradeDplain(GRADE_D_PLAIN_NEW);
		g.setGradeDminus(GRADE_D_MINUS_NEW);
		g.setGradeE(GRADE_E_NEW);
		assertTrue(store.putGradingSystem(g)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO#updateGradingSystem(com.yahoo.petermwenda83.bean.exam.GradingSystem)}.
	 */
	//@Ignore
	@Test
	public final void testUpdateGradingSystem() {
		store = new GradingSystemDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		GradingSystem g = new GradingSystem();
		g.setUuid(UUID_NEW);
		g.setSchoolAccountUuid(SCHOOL_UUID);
		g.setGradeAplain(GRADE_A_UPDATE);
		g.setGradeAminus(GRADE_A_MINUS_UPDATE);
		g.setGradeBplus(GRADE_B_PLUS_UPDATE);
		g.setGradeBplain(GRADE_B_PLAIN_UPDATE);
		g.setGradeBminus(GRADE_B_MINUS_UPDATE);
		g.setGradeCplus(GRADE_C_PLUS_UPDATE);
		g.setGradeCplain(GRADE_C_PLAIN_UPDATE);
		g.setGradeCminus(GRADE_C_MINUS_UPDATE);
		g.setGradeDplus(GRADE_D_PLUS_UPDATE);
		g.setGradeDplain(GRADE_D_PLAIN_UPDATE);
		g.setGradeDminus(GRADE_D_MINUS_UPDATE);
		g.setGradeE(GRADE_E_UPDATE);
		assertTrue(store.updateGradingSystem(g));  
	}

}
