/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.staff.StaffDetails;

/**
 * @author peter
 *
 */
public class TestStaffDetailsDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private StaffDetailsDAO store;
	
	final String UUID = "38EFA2D4-352D-4BC0-887F-9CA227950501",
			     UUID_NEW = "BAFF69CC-7917-48FB-A4D7-89878A9D53B1";
	
	final String SATFF_UUID = "708D2D79-BD0A-4338-BC75-62A5659A4F56",
			     SATFF_UUID_NEW = "5FC9F25F-7BC4-451F-B718-647HDHS462V";
	
	final String EMP_NUMBER = "1234",
			     EMP_NUMBER_NEW = "3456",
			     EMP_NUMBER_UPDATE = "1111";
	
	final String FIRST_NAME = "DOREEN",
			     FIRST_NAME_NEW = "Joy",
			     FIRST_NAME_UPDATE = "aaaa";
	
	final String LAST_NAME = "MUCHIRI",
			     LAST_NAME_NEW = "Kathure",
			     LAST_NAME_UPDATE = "bbbb";
	
	final String SUR_NAME = "NJERI",
			     SUR_NAME_NEW = "Micheni",
			     SUR_NAME_UPDATE = "cccc";
	
	final String GENDER = "MALE",
			     GENDER_NEW = "FEMALE",
			     GENDER_UPDATE = "dddd";
	
	final String NHIFNO = "N393",
			     NHIFNO_NEW = "N672",
			     NHIFNO_UPDATE = "eee";
	
	final String NSSNO = "H883",
			     NSSNO_NEW = "H771",
			     NSSNO_UPDATE = "ffff";
	
	final String PHONE = "7736636633",
			     PHONE_NEW = "071455661",
			     PHONE_UPDATE = "ggggg";
	
	final String DOB = "1992",
			     DOB_NEW = "1994",
			     DOB_UPDATE = "hhhh";
	
	final String NATIOANL_ID = "32345",
			     NATIOANL_ID_NEW = "237811",
			     NATIOANL_ID_UPDATE = "iiii";
	
	final String COUNTY = "Kerogoya",
			     COUNTY_NEW = "Meru",
			     COUNTY_UPDATE = "jjj";
	
	final String SYS_USER = "Peter",
			     SYS_USER_NEW = "Peter",
			     SYS_USER_UPDATE = "kkkk";
	
	final String RED_DATE = "",
			     RED_DATE_NEW = "";
	

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO#getStaffDetail(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetStaffDetail() {
		store = new StaffDetailsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StaffDetails staff = new StaffDetails();
		staff = store.getStaffDetail(SATFF_UUID);
		assertEquals(staff.getUuid(),UUID);
		assertEquals(staff.getStaffUuid(),SATFF_UUID);		
		assertEquals(staff.getEmployeeNo(),EMP_NUMBER);
		assertEquals(staff.getFirstName(),FIRST_NAME);
		assertEquals(staff.getLastName(),LAST_NAME);
		assertEquals(staff.getSurname(),SUR_NAME);
		assertEquals(staff.getGender(),GENDER);
		assertEquals(staff.getNhifNo(),NHIFNO);
		assertEquals(staff.getNssfNo(),NSSNO);
		assertEquals(staff.getPhone(),PHONE);
		assertEquals(staff.getdOB(),DOB);		
		assertEquals(staff.getNationalID(),NATIOANL_ID);
		assertEquals(staff.getCounty(),COUNTY);
		assertEquals(staff.getSysUser(),SYS_USER);
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO#putSStaffDetail(com.yahoo.petermwenda83.bean.staff.StaffDetails)}.
	 */
	@Ignore
	@Test
	public void testPutSStaffDetail() {
		store = new StaffDetailsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StaffDetails s = new StaffDetails();
		s.setUuid(SATFF_UUID_NEW);
		s.setStaffUuid(SATFF_UUID_NEW); 
		s.setEmployeeNo(EMP_NUMBER_NEW);
		s.setFirstName(FIRST_NAME_NEW);
		s.setLastName(LAST_NAME_NEW);
		s.setSurname(SUR_NAME_NEW);
		s.setGender(GENDER_NEW);
		s.setNhifNo(NHIFNO_NEW);
		s.setNssfNo(NSSNO_NEW);
		s.setPhone(PHONE_NEW);
		s.setdOB(DOB_NEW);
		s.setNationalID(NATIOANL_ID_NEW);
		s.setCounty(COUNTY_NEW);
		s.setSysUser(SYS_USER_NEW);
		assertTrue(store.putSStaffDetail(s));
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO#updateSStaffDetail(com.yahoo.petermwenda83.bean.staff.StaffDetails)}.
	 */
	//@Ignore
	@Test
	public void testUpdateSStaffDetail() {
		store = new StaffDetailsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StaffDetails s = new StaffDetails();
		s.setUuid(SATFF_UUID_NEW);
		s.setStaffUuid(SATFF_UUID_NEW); 
		s.setEmployeeNo(EMP_NUMBER_UPDATE);
		s.setFirstName(FIRST_NAME_UPDATE);
		s.setLastName(LAST_NAME_UPDATE);
		s.setSurname(SUR_NAME_UPDATE);
		s.setGender(GENDER_UPDATE);
		s.setNhifNo(NHIFNO_UPDATE);
		s.setNssfNo(NSSNO_UPDATE);
		s.setPhone(PHONE_UPDATE);
		s.setdOB(DOB_UPDATE);
		s.setNationalID(NATIOANL_ID_UPDATE);
		s.setCounty(COUNTY_UPDATE);
		s.setSysUser(SYS_USER_UPDATE);
		assertTrue(store.updateSStaffDetail(s)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO#deleteSStaffDetail(com.yahoo.petermwenda83.bean.staff.StaffDetails)}.
	 */
	@Ignore
	@Test
	public void testDeleteSStaffDetail() {
		store = new StaffDetailsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StaffDetails staff = new StaffDetails();
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO#getSStaffDetailList()}.
	 */
	@Ignore
	@Test
	public void testGetSStaffDetailList() {
		store = new StaffDetailsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StaffDetails> list = store.getSStaffDetailList();
		for (StaffDetails ss : list) {
			System.out.println(ss);
		}
	}

}
