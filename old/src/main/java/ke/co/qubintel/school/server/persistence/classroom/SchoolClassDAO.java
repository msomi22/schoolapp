/**
 * 
 */
package ke.co.qubintel.school.server.persistence.classroom;

import java.util.List;

import ke.co.qubintel.school.server.bean.classroom.ClassRoom;

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
	
	public ClassRoom getClassRoomByDesc(String accountId, String description);
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
