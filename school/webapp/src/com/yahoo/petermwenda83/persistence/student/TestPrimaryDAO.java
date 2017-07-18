/**
 * 
 */
package com.yahoo.petermwenda83.persistence.student;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.student.StudentPrimary;

/**
 * @author peter
 *
 */
public class TestPrimaryDAO {
	

	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	PrimaryDAO store;
	
	final String UUID = "171D8A46-4359-424C-B994-23FF4CD7C0E8",
			     UUID_NEW = "8D63C6C2-1D3A-41A3-A3D1-885A3B54C1FA";
	
	final String STUDENT_UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
	
	final String SCHOOL_NAME = "Kathituni Boarding",
			     SCHOOL_NAME_NEW = "new",
			     SCHOOL_NAME_UPDATE = "update";
	
	final String INDEX = "308098002",
			     INDEX_NEW = "new",
			     INDEX_UPDATE = "update";
	
	final String KCPE_YEAR = "2007",
			     KCPE_YEAR_NEW = "new",
			     KCPE_YEAR_UPDATE = "update";
	
	final String KCPE_MARK = "397",
			     KCPE_MARK_NEW = "new",
			     KCPE_MARK_UPDATE = "update";

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.PrimaryDAO#getPrimary(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetPrimary() {
		store = new PrimaryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentPrimary sp = new StudentPrimary();
		sp = store.getPrimary(STUDENT_UUID);
		assertEquals(sp.getUuid(),UUID);
		assertEquals(sp.getStudentUuid(),STUDENT_UUID);
		assertEquals(sp.getSchoolname(),SCHOOL_NAME);
		assertEquals(sp.getIndex(),INDEX);
		assertEquals(sp.getKcpeyear(),KCPE_YEAR);
		assertEquals(sp.getKcpemark(),KCPE_MARK);
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.PrimaryDAO#putPrimary(com.yahoo.petermwenda83.bean.student.StudentPrimary)}.
	 */
	@Ignore
	@Test
	public void testPutPrimary() {
		store = new PrimaryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentPrimary sp = new StudentPrimary();
		sp.setUuid(UUID_NEW);
		sp.setStudentUuid(STUDENT_UUID);
		sp.setSchoolname(SCHOOL_NAME_NEW);
		sp.setIndex(INDEX_NEW);
		sp.setKcpeyear(KCPE_YEAR_NEW);
		sp.setKcpemark(KCPE_MARK_NEW); 
		assertTrue(store.putPrimary(sp));
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.PrimaryDAO#updatePrimary(com.yahoo.petermwenda83.bean.student.StudentPrimary)}.
	 */
	@Ignore
	@Test
	public void testUpdatePrimary() {
		store = new PrimaryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentPrimary sp = new StudentPrimary();
		sp.setUuid(UUID_NEW);
		sp.setStudentUuid(STUDENT_UUID);
		sp.setSchoolname(SCHOOL_NAME_UPDATE);
		sp.setIndex(INDEX_UPDATE);
		sp.setKcpeyear(KCPE_YEAR_UPDATE);
		sp.setKcpemark(KCPE_MARK_UPDATE); 
		assertTrue(store.updatePrimary(sp));
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.PrimaryDAO#deletePrimary(com.yahoo.petermwenda83.bean.student.StudentPrimary)}.
	 */
	@Ignore
	@Test
	public void testDeletePrimary() {
		store = new PrimaryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentPrimary sp = new StudentPrimary();
		sp.setUuid(UUID_NEW);
		sp.setStudentUuid(STUDENT_UUID);
		assertTrue(store.deletePrimary(sp)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.PrimaryDAO#getAllPrimary()}.
	 */
	@Ignore
	@Test
	public void testGetAllPrimary() {
		store = new PrimaryDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentPrimary> list = store.getAllPrimary();
		for (StudentPrimary p : list) {
			System.out.println(p);	
		}
	}

}
