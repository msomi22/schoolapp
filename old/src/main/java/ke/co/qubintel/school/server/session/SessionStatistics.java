/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * This class manages online users
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */

public class SessionStatistics implements Serializable {

	//They are used to keep userId against online status
	private Map<String, String> userStatus;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6516924608718437954L;

	public SessionStatistics() {
		userStatus = new HashMap<>();
	}

	/**
	 * @return the userStatus
	 */
	public Map<String, String> getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(Map<String, String> userStatus) {
		this.userStatus = userStatus;
	}

	//anonymous class
	class onlineUser{
		private String userid;
		private String onlinestatus;
		public onlineUser(){
			userid = "";
			onlinestatus = "";
		}
		/**
		 * @return the userid
		 */
		public String getUserid() {
			return userid;
		}
		/**
		 * @param userid the userid to set
		 */
		public void setUserid(String userid) {
			this.userid = userid;
		}
		/**
		 * @return the onlinestatus
		 */
		public String getOnlinestatus() {
			return onlinestatus;
		}
		/**
		 * @param onlinestatus the onlinestatus to set
		 */
		public void setOnlinestatus(String onlinestatus) {
			this.onlinestatus = onlinestatus;
		}

	}

}

