/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.util.List;

import com.yahoo.petermwenda83.bean.staff.StaffDetails;

/**
 * @author peter
 *
 */
public interface SchoolStaffDetailsDAO {
	
	/**
	 * 
	 * @param staffUuid
	 * @return
	 */
	public StaffDetails getStaffDetail(String staffUuid);
	
	/**
	 * 
	 * @param staffUuid
	 * @return
	 */
	public StaffDetails getStaffDetailByemployeeNo(String employeeNo);
	 /**
	  * 
	  * @param staffDetail
	  * @return
	  */
	public boolean putSStaffDetail (StaffDetails staffDetail);
	 /**
	  * 
	  * @param staffDetail
	  * @return
	  */
	public boolean updateSStaffDetail (StaffDetails staffDetail);
	 /**
	  * 
	  * @param staffDetail
	  * @return
	  */
	public boolean deleteSStaffDetail (StaffDetails staffDetail);
	  /**
	   * 
	   * @return
	   */
	public List<StaffDetails> getSStaffDetailList ();

}
