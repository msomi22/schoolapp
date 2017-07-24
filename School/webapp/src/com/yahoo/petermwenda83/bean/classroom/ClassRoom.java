/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.bean.classroom;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 *  A stream object in a school
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ClassRoom  extends StorableBean{
	
	/** 
	 *  
	 */
	private static final long serialVersionUID = 6259060888065917342L;
	private String SchoolAccountUuid;
	private String RoomName;
	
	/**
	 * 
	 */
	public ClassRoom() {
		super();
		SchoolAccountUuid ="";
		RoomName ="";
		
	}
	
	
	/**
	 * @return the roomName
	 */
	public String getRoomName() {
		return RoomName;
	}


	/**
	 * @param roomName the roomName to set
	 */
	public void setRoomName(String roomName) {
		RoomName = roomName;
	}



	/**
	 * @return the schoolAccountUuid
	 */
	public String getSchoolAccountUuid() {
		return SchoolAccountUuid;
	}

	/**
	 * @param schoolAccountUuid the schoolAccountUuid to set
	 */
	public void setSchoolAccountUuid(String schoolAccountUuid) {
		SchoolAccountUuid = schoolAccountUuid;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("[ ClassRoom");
		builder.append(",getUuid()=");
		builder.append( getUuid());
		builder.append(", SchoolAccountUuid =");
		builder.append(SchoolAccountUuid);
		builder.append(", RoomName =");
		builder.append(RoomName);
		builder.append("]");
		return builder.toString(); 
		}


}
