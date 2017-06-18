/**
 * SchoolAccount Management System
 * This software belong to Peter Mwenda's and Miwgi Ndungu's Company
 * copywrite peter&MigwiSoftwares.co.ltd
 */
package com.yahoo.petermwenda83.persistence.subject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.subject.Subject;

/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TestSubjectDAO {
	

	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";

   final String  SUB_UUID = "D0F7EC32-EA25-7D32-8708-2CC132446A2E",
		       SUB_UUID_2 = "H0F7EC32-EA25-9D32-8708-2CC132446A2F",
		       SUB_UUID_3 ="4dcd3ab3-fffa-4083-89d5-c2734e0706a8";
               
   final String SUB_CODE = "ENG",
		     SUB_CODE_2 = "MATH",
		     SUB_CODE_3 = "KISW",
		     SUB_CODE_4 = "CHEMx";
   final String SUB_NAME = "English",
		     SUB_NAME_2 = "Mathematics",
		     SUB_NAME_3 ="Kiswahili", 
		     SUB_NAME_4 ="Chemistry";
final String SUB_CAT = "Compulsary",
             SUB_CAT_2 ="Humanity",
             SUB_CAT_3 ="Techinical",
             SUB_CAT_4 = "Science";
		


final String STU_UUID = "DAF7EC32-EA25-7D32-8708-2CC132446A2Y", 
             STU_UUID_2 ="DAF7EC32-EA25-7D32-8708-2CC132446A2Y",
             STU_UUID_3 ="66027e51-b1ad-4b10-8250-63af64d23323";
final String STUSUB_UUID = "650195B6-9357-C147-C24E-7FBDAEEC74ED", 
             STUSUB_UUID_2 ="e35a03bb-8bdb-4b9a-b9bd-ce24b16b0e27";

final String studentUuid = "DAF7EC32-EA25-7D32-8708-2CC132446A2Y";
final String uuid = "4a053537-b2cb-429a-a95b-fb529f692a2e";


  //final String SUB3UUID = "5fd784ea-3316-4d12-8e77-7862f0c5e084";

private SubjectDAO store;

	/**
	 * Subject method for {@link com.yahoo.petermwenda83.persistence.subject.SubjectDAO#getSubject(java.lang.String)}.
	 */
    @Ignore
	@Test
	public void testGetSubject() {
		store = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		Subject subject = new Subject();
		
		subject = store.getSubject(SUB_UUID);
		assertEquals(subject.getUuid(),SUB_UUID);
		/*assertEquals(subject.getSubjectcode(),SUB_CODE);
		assertEquals(subject.getSubjectname(),SUB_NAME);
		assertEquals(subject.getSubjectcategory(),SUB_CAT);*/
		
	}
     
     /**
     * Subject method for {@link com.yahoo.petermwenda83.persistence.subject.SubjectDAO#getSubjects(java.lang.String)}.
	 *
     */
    @Ignore
 	@Test
     public void testGetSubjects() {
 		store = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
 		
 		Subject subject = new Subject();
 		
 		subject = store.getSubjects(SUB_CODE);
 		assertEquals(subject.getUuid(),SUB_UUID);
 	/*	assertEquals(subject.getSubjectcode(),SUB_CODE);
 		assertEquals(subject.getSubjectname(),SUB_NAME);
 		assertEquals(subject.getSubjectcategory(),SUB_CAT);*/
 		
 	}
     
     


	/**
	 * SubjectUi method for {@link com.yahoo.petermwenda83.persistence.subject.SubjectDAO#putSubject(com.yahoo.petermwenda83.bean.student.Subject)}.
	 */
   @Ignore
	@Test
	public void testPutSubject() {
    	store = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	
    	Subject sub = new Subject();
    	String subUuid = sub.getUuid();
		sub.setUuid(subUuid);
		/*sub.setSubjectcode(SUB_CODE_4);
		sub.setSubjectname(SUB_NAME_4);
	    sub.setSubjectcategory(SUB_CAT_4); */
	    assertTrue(store.putSubject(sub));
	}

	

	/**
	 * SubjectUi method for {@link com.yahoo.petermwenda83.persistence.subject.SubjectDAO#editSubject(com.yahoo.petermwenda83.bean.student.Subject)}.
	 */
    @Ignore
	@Test
	public void testEditSubject() {
    	store = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
    	Subject sub = new Subject();
    /*	sub.setSubjectname(SUB_NAME_3);
    	sub.setSubjectcode(SUB_CODE_3);
    	sub.setSubjectcategory(SUB_CAT);*/
    	sub.setUuid(SUB_UUID_3);
    	assertTrue(store.editSubject(sub,SUB_UUID_3));
	}


	/**
	 * SubjectUi method for {@link com.yahoo.petermwenda83.persistence.subject.SubjectDAO#deleteStudent(com.yahoo.petermwenda83.bean.student.Subject)}.
	 */
	 @Ignore
	@Test
	public void testDeleteStudent() {
		 store = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
			Subject sub = new Subject();
			String uuid = "ff90787a-662b-4797-ac60-d0015910edba";
			sub.setUuid(uuid); 
			assertTrue(store.deleteStudent(sub));
	} 

	/**
	 * SubjectUi method for {@link com.yahoo.petermwenda83.persistence.subject.SubjectDAO#getAllStudent()}.
	 */
	//@Ignore
	@Test
	public void testGetAllStudent() {
		 store = new SubjectDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Subject> list = store.getAllSubjects();	
		//assertEquals(list.size(), 11);
		//System.out.println(list);
		for (Subject l : list) {
			System.out.println(l);
			
		}
		
	}

	
	
}
