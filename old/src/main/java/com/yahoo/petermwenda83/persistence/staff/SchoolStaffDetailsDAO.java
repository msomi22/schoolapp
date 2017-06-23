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
public interface SchoolStaffDetailsDAO {
	
	/**
	 * 
	 * @param staffUuid
	 * @return
	 */
	public Staff getStaffDetail(String staffUuid);
	
	/**
	 * 
	 * @param staffUuid
	 * @return
	 */
	public Staff getStaffDetailByemployeeNo(String employeeNo);
	 /**
	  * 
	  * @param staffDetail
	  * @return
	  */
	public boolean putSStaffDetail (Staff staffDetail);
	 /**
	  * 
	  * @param staffDetail
	  * @return
	  */
	public boolean updateSStaffDetail (Staff staffDetail);
	 /**
	  * 
	  * @param staffDetail
	  * @return
	  */
	public boolean deleteSStaffDetail (Staff staffDetail);
	  /**
	   * 
	   * @return
	   */
	public List<Staff> getSStaffDetailList ();

}
