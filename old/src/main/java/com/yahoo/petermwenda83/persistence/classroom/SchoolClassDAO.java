/**
 * 
 */
package com.yahoo.petermwenda83.persistence.classroom;

import java.util.List;

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;

/**
 * @author peter
 *
 */
public interface SchoolClassDAO {
	
	/**
	 * 
	 * @param Uuid
	 * @return
	 */
	public ClassRoom getClassRoom(String accountId, String uuid);
	 /**
	  * 
	  * @param Class
	  * @return
	  */
	public boolean putClassRoom(ClassRoom classRoom);
	 /**
	  * 
	  * @param Class
	  * @return
	  */
	public boolean updateClassRoom(ClassRoom classRoom);
	  /**
	   * 
	   * @return
	   */
	public List<ClassRoom> getClassRooms(String accountId);
	
	

}
