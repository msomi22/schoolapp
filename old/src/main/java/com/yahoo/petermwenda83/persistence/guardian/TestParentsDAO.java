/**
 * 
 */
package com.yahoo.petermwenda83.persistence.guardian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;

/**
 * @author peter
 *
 */
public class TestParentsDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private ParentsDAO store;
	
	final String UUID ="117A1059-A841-4F16-A429-6D5B69A289F0",
			     UUID_NEW ="473BC89C-419D-45D4-B721-E31785A574D3";
	
	final String STUDENT_UUID ="4F218688-6DE5-4E69-8690-66FBA2F0DC9F",
			     STUDENT_UUID_NEW ="72A995F4-0C94-4FAA-8A43-2277B02B97D1";
	
	final String FATHER_NAME ="Denis Murith",
			     FATHER_NAME_NEW ="new",
			     FATHER_NAME_UPDATE ="update";
	
	final String FATHER_PHONE ="25456777554",
			     FATHER_PHONE_NEW ="new",
			     FATHER_PHONE_UPDATE ="update";
	
	final String FATHER_OCCUPATION ="Bank Manager",
			     FATHER_OCCUPATION_NEW ="new",
			     FATHER_OCCUPATION_UPDATE ="update";
	
	final String FATHER_ID ="23322",
			     FATHER_ID_NEW ="new",
			     FATHER_ID_UPDATE ="update";
	
	final String FATHER_EMAIL ="moreen@gmail.com",
			     FATHER_EMAIL_NEW ="new",
			     FATHER_EMAIL_UPDATE ="update";
	
	final String MOTHER_NAME ="Moreen Makena",
			     MOTHER_NAME_NEW ="new",
			     MOTHER_NAME_UPDATE ="update";
	
	final String MOTHER_PHONE ="25456777554",
			     MOTHER_PHONE_NEW ="new",
			     MOTHER_PHONE_UPDATE ="update";
	
	final String MOTHER_OCCUPATION ="Teacher",
			     MOTHER_OCCUPATION_NEW ="new",
			     MOTHER_OCCUPATION_UPDATE ="update";
	
	final String MOTHER_EMAIL ="moreen@gmail.com",
			     MOTHER_EMAIL_NEW ="new",
			     MOTHER_EMAIL_UPDATE ="update";
	
	final String MOTHER_ID ="234444",
			     MOTHER_ID_NEW ="new",
			     MOTHER_ID_UPDATE ="update";
	
	final String RELATIVE_NAME ="Doris Ndungu",
			     RELATIVE_NAME_NEW ="new",
			     RELATIVE_NAME_UPDATE ="update";
	
	final String RELATIVE_PHONE ="25456777554",
			     RELATIVE_PHONE_NEW ="new",
			     RELATIVE_PHONE_UPDATE ="update";

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.guardian.ParentsDAO#getParent(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetParent() {
		store =new ParentsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentParent s = new StudentParent();
		s = store.getParent(STUDENT_UUID);
		assertEquals(s.getUuid(),UUID);
		assertEquals(s.getStudentUuid(),STUDENT_UUID);
		assertEquals(s.getFathername(),FATHER_NAME);
		assertEquals(s.getFatherphone(),FATHER_PHONE);
		assertEquals(s.getFatheroccupation(),FATHER_OCCUPATION);
		assertEquals(s.getFatherID(),FATHER_ID);
		assertEquals(s.getMothername(),MOTHER_NAME);
		assertEquals(s.getMotherphone(),MOTHER_PHONE);
		assertEquals(s.getMotheroccupation(),MOTHER_OCCUPATION);
		assertEquals(s.getMotherEmail(),MOTHER_EMAIL);
		assertEquals(s.getMotherID(),MOTHER_ID);
		assertEquals(s.getRelativeName(),RELATIVE_NAME);
		assertEquals(s.getRelativePhone(),RELATIVE_PHONE);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.guardian.ParentsDAO#putParent(com.yahoo.petermwenda83.bean.student.guardian.StudentParent)}.
	 */
	@Ignore
	@Test
	public void testPutParent() {
		store =new ParentsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentParent s = new StudentParent();
		s.setUuid(UUID_NEW);
		s.setStudentUuid(STUDENT_UUID_NEW);
		s.setFathername(FATHER_NAME_NEW);
		s.setFatherEmail(FATHER_EMAIL_NEW);
		s.setFatherphone(FATHER_PHONE_NEW);
		s.setFatherID(FATHER_ID_NEW);
		s.setFatheroccupation(FATHER_OCCUPATION_NEW);
		s.setMothername(MOTHER_NAME_NEW);
		s.setMotherphone(MOTHER_PHONE_NEW);
		s.setMotherEmail(FATHER_EMAIL_NEW);
		s.setMotherID(MOTHER_ID_NEW);
		s.setMotheroccupation(MOTHER_OCCUPATION_NEW);
		s.setRelativeName(RELATIVE_NAME_NEW);
		s.setRelativePhone(RELATIVE_PHONE_NEW);
		assertTrue(store.putParent(s)); 
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.guardian.ParentsDAO#updateParent(com.yahoo.petermwenda83.bean.student.guardian.StudentParent)}.
	 */
	@Ignore
	@Test
	public void testUpdateParent() {
		store =new ParentsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentParent s = new StudentParent();
		s.setUuid(UUID_NEW);
		s.setStudentUuid(STUDENT_UUID_NEW);
		s.setFathername(FATHER_NAME_UPDATE);
		s.setFatherEmail(FATHER_EMAIL_UPDATE);
		s.setFatherphone(FATHER_PHONE_UPDATE);
		s.setFatherID(FATHER_ID_UPDATE);
		s.setFatheroccupation(FATHER_OCCUPATION_UPDATE);
		s.setMothername(MOTHER_NAME_UPDATE);
		s.setMotherphone(MOTHER_PHONE_UPDATE);
		s.setMotherEmail(FATHER_EMAIL_UPDATE);
		s.setMotherID(MOTHER_ID_UPDATE);
		s.setMotheroccupation(MOTHER_OCCUPATION_UPDATE);
		s.setRelativeName(RELATIVE_NAME_UPDATE);
		s.setRelativePhone(RELATIVE_PHONE_UPDATE);
		assertTrue(store.updateParent(s));  
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.guardian.ParentsDAO#deleteParent(com.yahoo.petermwenda83.bean.student.guardian.StudentParent)}.
	 */
	@Ignore
	@Test
	public void testDeleteParent() {
		store =new ParentsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentParent s = new StudentParent();
		s.setStudentUuid(STUDENT_UUID_NEW);
		assertTrue(store.deleteParent(s));  
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.guardian.ParentsDAO#getParentList()}.
	 */
	@Ignore
	@Test
	public void testGetParentList() {
		store =new ParentsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentParent> list = store.getParentList();
		for (StudentParent sp : list) {
			System.out.println(sp);	
		}
	}

}
