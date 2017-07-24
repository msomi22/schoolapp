/**
 * 
 */
package com.yahoo.petermwenda83.persistence.student;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.student.StudentSubject;

/**
 * @author peter
 *
 */
public class TestStudentSubjectDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String UUID = "79B82D8A-34B1-4E18-B04D-010265997C1F",
			     UUID_NEW = "FC9EC66F-1952-4F4A-A70F-2769CB4796B2";
	
	final String STUDENT_UUID = "A195BAF6-D6E7-43A5-B7C9-D6C627A42815";
	
	final String SUBJECT_UUID = "D0F7EC32-EA25-7D32-8708-2CC132446",
			     SUBJECT_UUID_NEW = "4f59580d-1a16-4669-9ed5-4b89615d6903",
			     SUBJECT_UUID_UPDATE = "552c0a24-6038-440f-add5-2dadfb9a23bd";
	
	final String USER = "Peter",
		         USER_NEW = "new",
		         USER_UPDATE = "update";

   final Date DATE_IN = new Date();
	
	private StudentSubjectDAO store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO#getSubRoom(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetsubject() {
		store = new StudentSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		 StudentSubject sb = new StudentSubject();
		 sb.setStudentUuid(STUDENT_UUID);
		 sb.setSubjectUuid(SUBJECT_UUID); 
		 //sb = store.getsubject(sb);
	
		
	}
	
	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO#getSubRoom(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetSubRoom() {
		store = new StudentSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentSubject> list = store.getstudentSubList(STUDENT_UUID);	 
		for (StudentSubject l : list) {
			System.out.println(l);	
		}
		
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO#putSubRoom(com.yahoo.petermwenda83.bean.student.StudentSubject)}.
	 */
    @Ignore
	@Test
	public void testPutSubRoom() {
		 store = new StudentSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		 StudentSubject sb = new StudentSubject();
		 //sb.setUuid(UUID_NEW);
		 sb.setStudentUuid(STUDENT_UUID);
		 sb.setSubjectUuid(SUBJECT_UUID_NEW); 
		 sb.setSysUser(USER);
		 sb.setAllocationDate(DATE_IN); 
		 assertTrue(store.putstudentSub(sb));
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO#updateSubRoom(com.yahoo.petermwenda83.bean.student.StudentSubject)}.
	 */
    @Ignore
	@Test
	public void testUpdateSubRoom() {
		 store = new StudentSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		 StudentSubject sb = new StudentSubject();
		 sb.setUuid("14996816-68dd-4ef1-825c-af6393d66a40");
		 sb.setStudentUuid(STUDENT_UUID);
		 sb.setSubjectUuid(SUBJECT_UUID_UPDATE); 
		 sb.setSysUser(USER);
		 sb.setAllocationDate(DATE_IN); 
		 assertTrue(store.updatestudentSub(sb)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO#deleteSubRoom(com.yahoo.petermwenda83.bean.student.StudentSubject)}.
	 */
	@Ignore
	@Test
	public void testDeleteSubRoom() {
		store = new StudentSubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentSubject sb = new StudentSubject();
		 sb.setStudentUuid(STUDENT_UUID);
		 sb.setSubjectUuid(SUBJECT_UUID_UPDATE); 
		 assertTrue(store.deletestudentSub(sb));  
	}

	
}
