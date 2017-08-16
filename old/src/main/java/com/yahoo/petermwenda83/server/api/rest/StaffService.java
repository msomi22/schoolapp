/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;

/**
 * @author peter
 *
 */
public class StaffService {

	private static StaffDAO staffDAO;
	
	static{
		staffDAO = StaffDAO.getInstance();
	}
	
	
	public boolean putStaff(Staff staff){
		
		if(staffDAO.getStaffByStaffNo(staff.getAccountId(), staff.getStaffNo()) != null){
			return false;
			
		}else if(staffDAO.putStaff(staff)){
			return true;
			
		}else{
			return false;
		}
	}

}
