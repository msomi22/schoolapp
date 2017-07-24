package com.yahoo.petermwenda83.persistence.student;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;

public class TestStudentDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	
	final String CLASSROOM_UUID = "4DA86139-6A72-4089-8858-6A3A613FDFE6",
			     CLASSROOM_UUID_NEW = "4DA86139-6A72-4089-8858-6A3A613FDFE6",
			     CLASSROOM_UUID_UPDATE = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
	
	final String UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F",
			     UUID_NEW="56BC2892-A0D2-46E1-8627-5DFE27356A49";
	
	final String STATUS_UUID = "85C6F08E-902C-46C2-8746-8C50E7D11E2E",
			     STATUS_UUID_NEW = "6C03705B-E05E-420B-B5B8-C7EE36643E60";
	
	final String ADM_NO = "030/2012",
			     ADM_NO_NEW = "new",
			     ADM_NO_UPDATE = "update";
	
	final String FIRST_NAME = "JORAM",
			     FIRST_NAME_NEW = "new",
			     FIRST_NAME_UPDATE = "update";
	
	final String LAST_NAME = "NDUNGU",
			     LAST_NAME_NEW = "new",
			     LAST_NAME_UPDATE = "update";
	
	final String SURNAME = "MURIITHI",
			     SURNAME_NEW = "new",
			     SURNAME_UPDATE = "update";
	
	final String GENDER = "MALE",
			     GENDER_NEW = "new",
			     GENDER_UPDATE = "update";
	
	final String DOB = "11/06/94",
			     DOB_NEW = "new",
			     DOB_UPDATE = "update";
	
	final String B_CERT_NO = "L2374",
			     B_CERT_NO_NEW = "new",
			     B_CERT_NO_UPDATE = "update";
	
	final String COUNTY = "Nyeri",
			     COUNTY_NEW = "new",
			     COUNTY_UPDATE = "update";
	
	final String SYS_USER = "Peter",
			     SYS_USER_NEW = "new",
			     SYS_USER_UPDATE = "update";
	
	final Date ADM_DATE =  new Date();
	
	private StudentDAO store;
    
	@Ignore
	@Test
	public void testGetStudent() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Student s = new Student();
		s = store.getStudentByuuid(SCHOOL_UUID, UUID);
		assertEquals(s.getUuid(),UUID);
		assertEquals(s.getStatusUuid(),STATUS_UUID);
		assertEquals(s.getSchoolAccountUuid(),SCHOOL_UUID);
		assertEquals(s.getClassRoomUuid(),CLASSROOM_UUID);
		assertEquals(s.getAdmno(),ADM_NO);
		assertEquals(s.getFirstname(),FIRST_NAME);
		assertEquals(s.getLastname(),LAST_NAME);
		assertEquals(s.getSurname(),SURNAME);
		assertEquals(s.getGender(),GENDER);
		assertEquals(s.getdOB(),DOB);
		assertEquals(s.getBcertno(),B_CERT_NO);
		assertEquals(s.getCounty(),COUNTY);
		assertEquals(s.getSysUser(),SYS_USER);
		//assertEquals(s.getAdmissionDate(),ADM_DATE);
	}
   
	
	
	 
	@Ignore
	@Test
	public void testGetStudentADmNo() {
			store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
			Student s = new Student();
			String schoolUuid = "7e90f354-c1e0-4565-bccd-907aea188cf5";
			s = store.getStudentADmNo(SCHOOL_UUID);
			System.out.println("adm no = "+s.getAdmno());
		}
	
	
	 
		//@Ignore
		@Test
		public void testGetStudentCount() {
				store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
				equals(store.getStudentCount(STATUS_UUID, "Day")); 
				//System.out.println(store.getStudentCount(STATUS_UUID, "Day")); 
				
			}
	   
	@Ignore
	@Test
	public void testGetStudents() {
		   store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		   Student s = new Student();
		   s = store.getStudentObjByadmNo(SCHOOL_UUID,ADM_NO);
		    assertEquals(s.getUuid(),UUID);
			assertEquals(s.getStatusUuid(),STATUS_UUID);
			assertEquals(s.getSchoolAccountUuid(),SCHOOL_UUID);
			assertEquals(s.getClassRoomUuid(),CLASSROOM_UUID);
			assertEquals(s.getAdmno(),ADM_NO);
			assertEquals(s.getFirstname(),FIRST_NAME);
			assertEquals(s.getLastname(),LAST_NAME);
			assertEquals(s.getSurname(),SURNAME);
			assertEquals(s.getGender(),GENDER);
			assertEquals(s.getdOB(),DOB);
			assertEquals(s.getBcertno(),B_CERT_NO);
			assertEquals(s.getCounty(),COUNTY);
			assertEquals(s.getSysUser(),SYS_USER);
			//assertEquals(s.getAdmissionDate(),ADM_DATE);
	}
    
	@Ignore
	@Test
	public void testGetStudentByName() {
		 store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		 SchoolAccount schoolaccount = new SchoolAccount();
		 schoolaccount.setUuid(SCHOOL_UUID); 
		/* List<Student> list = store.getStudentByName(schoolaccount, "pe");
			for (Student l : list) {
				System.out.println(l);	
			}*/
	}
    
	@Ignore
	@Test
	public void testGetStudentByAdmNo() {
		 store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		 SchoolAccount schoolaccount = new SchoolAccount();
		 schoolaccount.setUuid(SCHOOL_UUID); 
		 List<Student> list = store.getStudentByAdmNo(SCHOOL_UUID, "ad"); 
			for (Student l : list) {
				System.out.println(l);	
			}
	}
	@Ignore
	@Test
	public void testPutStudents() {
		 store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		 Student s = new Student();
		 s.setUuid(UUID_NEW);
		 s.setSchoolAccountUuid(SCHOOL_UUID);
		 s.setStatusUuid(STATUS_UUID);
		 s.setClassRoomUuid(CLASSROOM_UUID_NEW);
		 s.setAdmno(ADM_NO_NEW);
		 s.setFirstname(FIRST_NAME_NEW);
		 s.setLastname(LAST_NAME_NEW);
		 s.setSurname(SURNAME_NEW);
		 s.setGender(GENDER_NEW);
		 s.setdOB(DOB_NEW);
		 s.setBcertno(B_CERT_NO_NEW);
		 s.setCounty(COUNTY_NEW); 
		 s.setSysUser(SYS_USER_NEW);
		 s.setAdmissionDate(ADM_DATE); 
		 assertTrue(store.putStudents(s));   

	}
	@Ignore
	@Test
	public void testUpdateStudents() {
		 store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		 Student s = new Student();
		 s.setSchoolAccountUuid(SCHOOL_UUID);
		 s.setStatusUuid(STATUS_UUID);
		 s.setUuid(UUID_NEW);
		 s.setClassRoomUuid(CLASSROOM_UUID_UPDATE);
		 s.setAdmno(ADM_NO_UPDATE);
		 s.setFirstname(FIRST_NAME_UPDATE);
		 s.setLastname(LAST_NAME_UPDATE);
		 s.setSurname(SURNAME_UPDATE);
		 s.setGender(GENDER_UPDATE);
		 s.setdOB(DOB_UPDATE);
		 s.setBcertno(B_CERT_NO_UPDATE);
		 s.setCounty(COUNTY_UPDATE); 
		 s.setSysUser(SYS_USER_UPDATE);
		 s.setAdmissionDate(ADM_DATE); 
		 assertTrue(store.updateStudents(s));  

	}
	 @Ignore
	@Test
	public void testDeleteStudents() {
		 store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		 Student s = new Student();
		 s.setSchoolAccountUuid(SCHOOL_UUID);
		 s.setStatusUuid(STATUS_UUID);
		 assertTrue(store.deleteStudents(s)); 

	}
	@Ignore
	@Test
	public void testGetAllStudents() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Student> list = store.getAllStudents(SCHOOL_UUID,CLASSROOM_UUID);
		for (Student l : list) {
			System.out.println(l);	
		}
		
	}
	@Ignore
	@Test
	public void testGetStudentList() {
		store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		 SchoolAccount schoolaccount = new SchoolAccount();
		 schoolaccount.setUuid(SCHOOL_UUID);
		List<Student> list = store.getStudentList(schoolaccount, 0, 15);
		for (Student l : list) {
			System.out.println(l);	
		}
	}
	 
	   
   @Ignore
   @Test
   public void testGetAllStudentList() {
	store = new StudentDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	List<Student> list = store.getAllStudentList(SCHOOL_UUID);
	for (Student l : list) {
				System.out.println(l);	
			}
		}

}
