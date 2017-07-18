/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.staff.TeacherDuties;

/**
 * @author peter
 *
 */
public class TestTeacherDutiesDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String UUID = "AD53EAE5-9015-44EB-8D3A-365D4911A4F4";
	final String TEACHER_UUID = "708D2D79-BD0A-4338-BC75-62A5659A4F56";
	final String DUTY_UUID = "C967E7CE-4F4F-4B0A-9812-0003DF5847EA";
	
	
    private TeacherDutiesDAO store;
	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.TeacherDutiesDAO#getTeacherDuties(java.lang.String)}.
	 */
   @Ignore
	@Test
	public void testGetTeacherDuties() {
		store = new TeacherDutiesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		TeacherDuties t = new TeacherDuties();
		t = store.getTeacherDuties(TEACHER_UUID);
		assertEquals(t.getUuid(),UUID);
		assertEquals(t.getTeacherUuid(),TEACHER_UUID);
		assertEquals(t.getDutyUuid(),DUTY_UUID);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.TeacherDutiesDAO#putTeacherDuties(com.yahoo.petermwenda83.bean.staff.TeacherDuties)}.
	 */
    @Ignore
	@Test
	public void testPutTeacherDuties() {
		store = new TeacherDutiesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.TeacherDutiesDAO#updateTeacherDuties(com.yahoo.petermwenda83.bean.staff.TeacherDuties)}.
	 */
    @Ignore
	@Test
	public void testUpdateTeacherDuties() {
		store = new TeacherDutiesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.TeacherDutiesDAO#deleteTeacherDuties(com.yahoo.petermwenda83.bean.staff.TeacherDuties)}.
	 */
    @Ignore
	@Test
	public void testDeleteTeacherDuties() {
		store = new TeacherDutiesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.TeacherDutiesDAO#getTeacherDutyList()}.
	 */
    @Ignore
	@Test
	public void testGetTeacherDutyList() {
		store = new TeacherDutiesDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<TeacherDuties> list = store.getTeacherDutyList();
		for (TeacherDuties ss : list) {
			System.out.println(ss);
		}
	}

}
