/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.staff.TeacherSubClass;

/**
 * @author peter
 *
 */
public class TestTeacherSubClassDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private TeacherSubClassDAO store;
	
	final String TEACHER_UUID = "F49DB775-4952-4915-B978-9D9F3E36D6E9",
			     TEACHER_UUID_NEW= "";
	
	final String SUB_UUID = "D0F7EC32-EA25-7D32-8708-2CC132446",
			     SUB_UUID_NEW ="0e5dc1c6-f62f-4a36-a1ec-064173332694",
			     SUB_UUID_UPDATE ="b9bbd718-b32f-4466-ab34-42f544ff900e";
	
	final String CLASS_UUID = "D0F7EC32-EA25-7D32-8708-2CC132446",
		         CLASS_UUID_NEW ="59E5F556-4B04-43B2-8139-E2D39A7836C6",
		         CLASS_UUID_UPDATE ="7E8BDC36-02E4-45F6-8EC6-AA5B95EA79D0";
	
	final String SYS_USER = "Peter",
		         SYS_USER_NEW = "Peter",
		         SYS_USER_UPDATE = "kkkk";

    final String RED_DATE = "",
		         RED_DATE_NEW = "";

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO#getSubjectClass(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetSubjectClass() {
		store = new TeacherSubClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		TeacherSubClass t = new TeacherSubClass();
		t = store.getSubjectClass(TEACHER_UUID); 
		assertEquals(t.getSubjectUuid(),SUB_UUID);
	}
	
	@Ignore
	@Test
	public void testGetSubjectsANDClassesList() {
		store = new TeacherSubClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<TeacherSubClass> list = store.getSubjectsANDClassesList(TEACHER_UUID); 	
		for (TeacherSubClass l : list) {
			System.out.println(l);	
		}
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO#putSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubClass)}.
	 */
	@Ignore
	@Test
	public void testPutSubjectClass() {
		store = new TeacherSubClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		TeacherSubClass t = new TeacherSubClass();
		t.setTeacherUuid(TEACHER_UUID);
		t.setSubjectUuid(SUB_UUID_NEW);
		t.setClassRoomUuid(CLASS_UUID_NEW);
		t.setSysUser(SYS_USER); 
		assertTrue(store.putSubjectClass(t)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO#updateSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubClass)}.
	 */
	//@Ignore
	@Test
	public void testUpdateSubjectClass() {
		store = new TeacherSubClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		TeacherSubClass t = new TeacherSubClass();
		t.setTeacherUuid(TEACHER_UUID);
		t.setSubjectUuid(SUB_UUID_UPDATE);
		t.setClassRoomUuid(CLASS_UUID_UPDATE);
		t.setSysUser(SYS_USER); 
		assertTrue(store.updateSubjectClass(t));  
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO#deleteSubjectClass(com.yahoo.petermwenda83.bean.staff.TeacherSubClass)}.
	 */
	@Ignore
	@Test
	public void testDeleteSubjectClass() {
		store = new TeacherSubClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO#getSubjectClassList()}.
	 */
	@Ignore
	@Test
	public void testGetSubjectClassList() {
		store = new TeacherSubClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<TeacherSubClass> list = store.getSubjectClassList();	
		for (TeacherSubClass l : list) {
			System.out.println(l);	
		}
		
	}

}
