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
public interface SchoolRoomDAO {
	 /**
	  * 
	  * @param SchoolAccountUuid
	  * @param Uuid
	  * @return
	  */
	public ClassRoom getroom(String SchoolAccountUuid,String Uuid);
	/**
	 * 
	 * @param SchoolAccountUuid
	 * @param RoomName
	 * @return
	 */
	public ClassRoom getroomByRoomName(String SchoolAccountUuid,String RoomName);
	
	  /**
	   * 
	   * @param room
	   * @return
	   */
	public boolean putroom(ClassRoom room);
	   /**
	    * 
	    * @param room
	    * @return
	    */
	public boolean updateroom(ClassRoom room);
	   /**
	    * 
	    * @param room
	    * @return
	    */
	public boolean deleteroom(ClassRoom room);
	  /**
	   * 
	   * @param SchoolAccountUuid
	   * @return
	   */
	public List<ClassRoom> getAllRooms(String SchoolAccountUuid);
	

}
