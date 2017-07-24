/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.util.List;

import com.yahoo.petermwenda83.bean.staff.Staff;

/**
 * @author peter
 *
 */
public interface SchoolStaffDAO {
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param Uuid
	 * @return
	 */
	public Staff getStaff(String schoolAccountUuid,String Uuid);
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param username
	 * @return
	 */
	public Staff getStaffByUsername(String schoolAccountUuid,String username);
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public Staff getStaffUsername(String username);
	 /**
	  * 
	  * @param schoolAccountUuid
	  * @param PositionUuid
	  * @return
	  */
	public Staff getStaffByPosition(String schoolAccountUuid,String PositionUuid);
	 /**
	  * 
	  * @param staff
	  * @return
	  */
	public boolean putStaff(Staff staff);
	 /**
	  * 
	  * @param staff
	  * @return
	  */
	public boolean updateStaff(Staff staff);
	 /**
	  * 
	  * @param staff
	  * @return
	  */
	public boolean deleteStaff(Staff staff);
	  /**
	   * 
	   * @param schoolAccountUuid
	   * @return
	   */
	public List<Staff> getStaffList(String schoolAccountUuid); 
	/**
	 * 
	 * @param schoolAccountUuid
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public List<Staff> getStaffList(String schoolAccountUuid, int startIndex , int endIndex); 

}
