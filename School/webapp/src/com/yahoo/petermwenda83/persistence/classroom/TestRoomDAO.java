/**
 * 
 */
package com.yahoo.petermwenda83.persistence.classroom;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;

/**
 * @author peter
 *
 */
public class TestRoomDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String UUID = "4DA86139-6A72-4089-8858-6A3A613FDFE6",
			     UUID_NEW = "301A678C-1913-454A-8814-884D6613EF93";
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	
	final String ROOM_NAME = "FORM 1 N",
			     ROOM_NAME_NEW = "new",
			     ROOM_NAME_UPDATE= "update";
	
	
	private RoomDAO store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.classroom.RoomDAO#getroom(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetroom() {
		store = new RoomDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		ClassRoom c = new ClassRoom();
		c = store.getroom(SCHOOL_UUID, UUID);
		assertEquals(c.getUuid(),UUID);
		assertEquals(c.getSchoolAccountUuid(),SCHOOL_UUID);
		assertEquals(c.getRoomName(),ROOM_NAME);
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.classroom.RoomDAO#putroom(com.yahoo.petermwenda83.bean.classroom.ClassRoom)}.
	 */
	@Ignore
	@Test
	public void testPutroom() {
		store = new RoomDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		ClassRoom c = new ClassRoom();
		c.setUuid(UUID_NEW);
		c.setSchoolAccountUuid(SCHOOL_UUID);
		c.setRoomName(ROOM_NAME_NEW);
		assertTrue(store.putroom(c)); 
		
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.classroom.RoomDAO#updateroom(com.yahoo.petermwenda83.bean.classroom.ClassRoom)}.
	 */
	@Ignore
	@Test
	public void testUpdateroom() {
		store = new RoomDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		ClassRoom c = new ClassRoom();
		c.setUuid(UUID_NEW);
		c.setSchoolAccountUuid(SCHOOL_UUID);
		c.setRoomName(ROOM_NAME_UPDATE);
		assertTrue(store.updateroom(c));  
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.classroom.RoomDAO#deleteroom(com.yahoo.petermwenda83.bean.classroom.ClassRoom)}.
	 */
	@Ignore
	@Test
	public void testDeleteroom() {
		store = new RoomDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		ClassRoom c = new ClassRoom();
		c.setUuid(UUID_NEW);
		c.setSchoolAccountUuid(SCHOOL_UUID);
		assertTrue(store.deleteroom(c)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.classroom.RoomDAO#getAllRooms(java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetAllRooms() {
		store = new RoomDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<ClassRoom> list = store.getAllRooms(SCHOOL_UUID);
		for (ClassRoom cl : list) {
			System.out.println(cl); 
		}
	}

}
