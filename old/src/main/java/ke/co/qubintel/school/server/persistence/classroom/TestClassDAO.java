package ke.co.qubintel.school.server.persistence.classroom;
/**
 * 
 *//*
package com.yahoo.petermwenda83.persistence.classroom;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.classroom.Stream;

*//**
 * @author peter
 *
 *//*
public class TestClassDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	private ClassDAO store;
	
	final String UUID = "C143978A-E021-4015-BC67-5A00D6C910D1",
			     UUID_NEW = "46149579-0EBD-4C38-BF51-591455D3F946";
	
	final String CLASS_NAME = "FORM 1",
			     CLASS_NAME_NEW = "new",
			     CLASS_NAME_UPDATE = "update";

	*//**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.classroom.ClassDAO#getClass(java.lang.String)}.
	 *//*
	@Ignore
	@Test
	public void testGetClassString() {
		store = new ClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Stream c = new Stream();
		//c = store.getClass(UUID);
		//assertEquals(c.getClassName(),CLASS_NAME);
	}
	
	*//**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.classroom.ClassDAO#getClass(java.lang.String)}.
	 *//*
	@Ignore
	@Test
	public void testPutClass() {
		store = new ClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Stream c = new Stream();
		c.setUuid(UUID_NEW);
		//c.setClassName(CLASS_NAME_NEW); 
		//assertTrue(store.putClass(c)); 
		
	}
	
	*//**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.classroom.ClassDAO#getClass(java.lang.String)}.
	 *//*
	@Ignore
	@Test
	public void testUpdateClass() {
		store = new ClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Stream c = new Stream();
		c.setUuid(UUID_NEW);
		//c.setClassName(CLASS_NAME_UPDATE); 
		//assertTrue(store.updateClass(c));  

	}
	
	@Ignore
	@Test
	public void testGetClassList() {
		store = new ClassDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		List<ClassRoom> list = store.getClassRooms("E3CDC578-37BA-4CDB-B150-DAB0409270CD");
		for (ClassRoom c : list) {
			System.out.println(c);
		}
	}

}
*/