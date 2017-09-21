/**
 * 
 */
package com.yahoo.petermwenda83.persistence.classroom;

import java.util.List;

import com.yahoo.petermwenda83.bean.classroom.Stream;

/**
 * @author peter
 *
 */
public interface SchoolStreamDAO {
	 /**
	  * 
	  * @param accountId
	  * @param Uuid
	  * @return
	  */
	public Stream getStream(String accountId,String uuid);
	/**
	 * 
	 * @param accountId
	 * @param RoomName
	 * @return
	 */
	public Stream getStreamByDesc(String accountId, String description);
	/**
	 * 
	 * @param accountId
	 * @param description
	 * @return
	 */
	public List<Stream> findDuplicate(String accountId, String description);
	
	  /**
	   * 
	   * @param room
	   * @return
	   */
	public boolean putStream(Stream stream);
	   /**
	    * 
	    * @param room
	    * @return
	    */
	public boolean updateStream(Stream stream);
	   /**
	    * 
	    * @param room
	    * @return
	    */
	public boolean deleteStream(String accountId,String uuid);
	  /**
	   * 
	   * @param accountId
	   * @return
	   */
	public List<Stream> getStreamList(String accountId);
	/**
	 * 
	 * @param accountId
	 * @param classRoomId
	 * @return
	 */
	public List<Stream> getStreamList(String accountId , String classRoomId);
	

}
