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
	 * @param accountId
	 * @param Uuid
	 * @return
	 */
	public Staff getStaff(String accountId, String uuid);
	/**
	 * 
	 * @param accountId
	 * @param staffNo
	 * @return
	 */
	public Staff getStaffByStaffNo(String accountId, String staffNo);
	/**
	 * 
	 * @param accountId
	 * @param username
	 * @return
	 */
	public Staff getStaffByUsername(String accountId, String username);
	/**
	 * 
	 * @param accountId
	 * @param acessLevelId
	 * @return
	 */
	public Staff getStaffByAccessLevel(String accountId, String acessLevelId);
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
	  * @param accountId
	  * @param Uuid
	  * @return
	  */
	public boolean deleteStaff(String accountId, String uuid);
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public List<Staff> getStaff(String accountId); 
	/**
	 * 
	 * @param accountId
	 * @param acessLevelId
	 * @return
	 */
	public int getStaffAccessLevel(String accountId, String acessLevelId);
	/**
	 * 
	 * @param accountId
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public List<Staff> getStaff(String accountId, int startIndex , int endIndex); 

}
