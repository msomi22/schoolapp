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

package ke.co.qubintel.school.server.bean.classroom;

import ke.co.qubintel.school.server.bean.StorableBean;

/** 
 *  A class object in a school
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Stream extends StorableBean{
	
	private String classRoomId;
	private String description;

	/**
	 * 
	 */
	public Stream() {
		classRoomId = "";
		description = "";
	}
   


	/**
	 * @return the classRoomId
	 */
	public String getClassRoomId() {
		return classRoomId;
	}



	/**
	 * @param classRoomId the classRoomId to set
	 */
	public void setClassRoomId(String classRoomId) {
		this.classRoomId = classRoomId;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description.toUpperCase();
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Stream");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(", accountId =");
		builder.append(getAccountId());
		builder.append(", classRoomId =");
		builder.append(classRoomId);
		builder.append(", description =");
		builder.append(description);
		return builder.toString(); 
		}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2355498812294520397L;
	
}
