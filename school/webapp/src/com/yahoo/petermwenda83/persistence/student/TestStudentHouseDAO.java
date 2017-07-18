package com.yahoo.petermwenda83.persistence.student;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.student.StudentHouse;

public class TestStudentHouseDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String UUID = "74CFE4DE-BF85-4558-B791-B1D1CBDC892C",
			     UUID_NEW = "A96EF005-B34E-4ABB-9B26-5B01466CAD91";
	
	final String STUDENT_UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
	
	final String HOUSE_UUID = "C69AEA6C-AFEF-4AD6-9779-0067C9C77A61",
			     HOUSE_UUID_NEW = "2A88D2A1-8CD4-4797-86EE-61A9C334C523",
			     HOUSE_UUID_UPDATE = "5BFFCE33-7FCD-4199-9BFA-A32BABC4D370";
	
	final String USER = "Peter",
			     USER_NEW = "new",
			     USER_UPDATE = "update";
	
	final Date DATE_IN = new Date();
	
	private StudentHouseDAO store;
	@Ignore
	@Test
	public void testGetHouse() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentHouse h = new StudentHouse();
		h = store.getHouse(STUDENT_UUID);
		assertEquals(h.getStudentUuid(),STUDENT_UUID);
		assertEquals(h.getUuid(),UUID);
		assertEquals(h.getHouseUuid(),HOUSE_UUID);
		assertEquals(h.getSysUser(),USER);
		//assertEquals(h.getDateIn(),DATE_IN);
		
	}
	
	@Ignore
	@Test
	public void testGetHouseByHoudeId() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentHouse h = new StudentHouse();
		h = store.getHouseByHoudeId(HOUSE_UUID,STUDENT_UUID); 
		assertEquals(h.getStudentUuid(),STUDENT_UUID);
		//assertEquals(h.getUuid(),UUID);
		//assertEquals(h.getHouseUuid(),HOUSE_UUID);
		//assertEquals(h.getSysUser(),USER);
		//assertEquals(h.getDateIn(),DATE_IN);
		
	}
	
	//@Ignore
	@Test
	public void testGetHouseList1() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentHouse> list = store.getHouseList(HOUSE_UUID);
		for (StudentHouse l : list) {
			System.out.println(l);	
		}
		
	}
	@Ignore
	@Test
	public void testPutHouse() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentHouse h = new StudentHouse();
		h.setUuid(UUID_NEW);
		h.setStudentUuid(STUDENT_UUID);
		h.setHouseUuid(HOUSE_UUID_NEW);
		h.setSysUser(USER_NEW);
		h.setDateIn(DATE_IN);
		assertTrue(store.putHouse(h)); 
	}
	@Ignore
	@Test
	public void testUpdatetHouse() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentHouse h = new StudentHouse();
		h.setUuid(UUID_NEW);
		h.setStudentUuid(STUDENT_UUID);
		h.setHouseUuid(HOUSE_UUID_UPDATE);
		h.setSysUser(USER_UPDATE);
		h.setDateIn(DATE_IN);
		assertTrue(store.updatetHouse(h));  
	}
    @Ignore
	@Test
	public void testDeleteHouse() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentHouse h = new StudentHouse();
		h.setStudentUuid(STUDENT_UUID);
		assertTrue(store.deleteHouse(h));   
	}
	@Ignore
	@Test
	public void testGetHouseList() {
		store = new StudentHouseDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentHouse> list = store.getHouseList();
		for (StudentHouse l : list) {
			System.out.println(l);	
		}
	}

}
