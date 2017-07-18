/**
 * 
 */
package com.yahoo.petermwenda83.persistence.guardian;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.classroom.Classes;
import com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor;

/**
 * @author peter
 *
 */
public class TestSponsorsDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private SponsorsDAO store;
	
	final String UUID = "848A5C55-27C3-4D32-91FF-75AD69B947FF",
			     UUID_NEW = "E9383928-B5EA-4279-B4E7-7C30EBC1EFCA";
	
	final String STUDENT_UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F",
			     STUDENT_UUID_NEW = "72A995F4-0C94-4FAA-8A43-2277B02B97D1";
	
	final String SPONSOR_NAME = "FasTech Solutions",
			     SPONSOR_NAME_NEW = "new",
			     SPONSOR_NAME_UPDATE = "update";
	
	final String SPONSOR_PHONE = "254718953974",
			     SPONSOR_PHONE_NEW = "new",
			     SPONSOR_PHONE_UPDATE = "update";
	
	final String SPONSOR_OCCUPATION = "Company",
			     SPONSOR_OCCUPATION_NEW = "new",
			     SPONSOR_OCCUPATION_UPDATE = "update";
	
	final String SPONSOR_COUNTRY = "Kenya",
			     SPONSOR_COUNTRY_NEW = "new",
			     SPONSOR_COUNTRY_UPDATE = "update";
	
	final String SPONSOR_COUNTY = "Tharaka Nithi",
			     SPONSOR_COUNTY_NEW = "new",
			     SPONSOR_COUNTY_UPDATE = "update";

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.guardian.SponsorsDAO#getSponsor(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetSponsor() {
		store = new SponsorsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentSponsor s = new StudentSponsor();
		s = store.getSponsor(STUDENT_UUID);
		assertEquals(s.getUuid(),UUID);
		assertEquals(s.getStudentUuid(),STUDENT_UUID);
		assertEquals(s.getSponsorName(),SPONSOR_NAME);
		assertEquals(s.getSponsorPhone(),SPONSOR_PHONE);
		assertEquals(s.getSponsorOccupation(),SPONSOR_OCCUPATION);
		assertEquals(s.getSponsorCountry(),SPONSOR_COUNTRY);
		assertEquals(s.getSponsorCounty(),SPONSOR_COUNTY);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.guardian.SponsorsDAO#putSponsor(com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor)}.
	 */
	@Ignore
	@Test
	public void testPutSponsor() {
		store = new SponsorsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentSponsor s = new StudentSponsor();
		s.setUuid(UUID_NEW);
		s.setStudentUuid(STUDENT_UUID_NEW);
		s.setSponsorName(SPONSOR_NAME_NEW);
		s.setSponsorPhone(SPONSOR_PHONE_NEW);
		s.setSponsorOccupation(SPONSOR_OCCUPATION_NEW);
		s.setSponsorCountry(SPONSOR_COUNTRY_NEW);
		s.setSponsorCounty(SPONSOR_COUNTY_NEW);
		assertTrue(store.putSponsor(s));
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.guardian.SponsorsDAO#updateSponsor(com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor)}.
	 */
	@Ignore
	@Test
	public void testUpdateSponsor() {
		store = new SponsorsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentSponsor s = new StudentSponsor();
		s.setUuid(UUID_NEW);
		s.setStudentUuid(STUDENT_UUID_NEW);
		s.setSponsorName(SPONSOR_NAME_UPDATE);
		s.setSponsorPhone(SPONSOR_PHONE_UPDATE);
		s.setSponsorOccupation(SPONSOR_OCCUPATION_UPDATE);
		s.setSponsorCountry(SPONSOR_COUNTRY_UPDATE);
		s.setSponsorCounty(SPONSOR_COUNTY_UPDATE);
		assertTrue(store.updateSponsor(s)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.guardian.SponsorsDAO#deleteSponsor(com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor)}.
	 */
	@Ignore
	@Test
	public void testDeleteSponsor() {
		store = new SponsorsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		StudentSponsor s = new StudentSponsor();
		s.setStudentUuid(STUDENT_UUID_NEW);
		assertTrue(store.deleteSponsor(s)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.guardian.SponsorsDAO#getStudentSponsorList()}.
	 */
	@Ignore
	@Test
	public void testGetStudentSponsorList() {
		store = new SponsorsDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<StudentSponsor> list = store.getStudentSponsorList();
		for (StudentSponsor s : list) {
			System.out.println(s);
		}
	}

}
