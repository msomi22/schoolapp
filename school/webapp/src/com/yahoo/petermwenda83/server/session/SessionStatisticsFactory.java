/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.server.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

//import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;

/**
 * This class get the online users and put their IDs in hash map  
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
 
public class SessionStatisticsFactory {
	
	private static StaffDAO staffDAO;
	private static final String onlinestatus = "Online";

    static {
    	staffDAO = StaffDAO.getInstance();
    }

	public static SessionStatistics getSessionStatistics(String accountuuid,String staffuuid) {
		SessionStatistics stats = new SessionStatistics();
		List<Staff> staffList = new ArrayList<>();
		staffList = staffDAO.getStaffList(accountuuid);
		Map<String, String> userStatus = new HashMap<>();
		for(Staff staff : staffList){
			if(StringUtils.equals(staff.getUuid(), staffuuid)){
			userStatus.put(staff.getUuid(), onlinestatus);
			stats.setUserStatus(userStatus); 
			}
		}
		
		String state = "";
		Map<String, String> uStatus = new HashMap<>();
		uStatus = stats.getUserStatus(); 
		if(uStatus.get(staffuuid)!=null){
		state = uStatus.get(staffuuid);
		}else{
			state = "Offline";
		}
		//System.out.println("state = " + state);
	
		return stats;
	}

    
}

