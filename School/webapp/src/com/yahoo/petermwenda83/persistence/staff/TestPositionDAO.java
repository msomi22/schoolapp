/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.staff.Position;

/**
 * @author peter
 *
 */
public class TestPositionDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
			     SCHOOL_UUID_NEW ="3A387B8B-A0D8-4F27-B9D2-E329619DF055";
	
	final String UUID = "C3915245-00EE-4EF4-9898-ACE59683DD60";
	
	final String POSITION = "Principal";
	
	private PositionDAO store;
	
	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.PositionDAO#get(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGet() {
		store = new PositionDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Position p = new Position();
		p = store.get(UUID);
		assertEquals(p.getPosition(),POSITION);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.PositionDAO#putPosition(com.yahoo.petermwenda83.bean.staff.Position)}.
	 */
	@Ignore
	@Test
	public void testPutPosition() {
		store = new PositionDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.PositionDAO#updatePosition(com.yahoo.petermwenda83.bean.staff.Position)}.
	 */
	@Ignore
	@Test
	public void testUpdatePosition() {
		store = new PositionDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.PositionDAO#deletePosition(com.yahoo.petermwenda83.bean.staff.Position)}.
	 */
	@Ignore
	@Test
	public void testDeletePosition() {
		store = new PositionDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.staff.PositionDAO#getPositionList(java.lang.String)}.
	 */
	//@Ignore
	@Test
	public void testGetPositionList() {
		store = new PositionDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<Position> list = store.getPositionList();
		for (Position ss : list) {
			System.out.println(ss);
		}
	}

}
