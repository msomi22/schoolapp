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
 *  A stream object in a school
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ClassRoom  extends StorableBean{

	private String description;
	private int examSubNumber;
	
	/**
	 * 
	 */
	public ClassRoom() {
		super();
		description ="";
		examSubNumber = 0;
		
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the examSubNumber
	 */
	public int getExamSubNumber() {
		return examSubNumber;
	}

	/**
	 * @param examSubNumber the examSubNumber to set
	 */
	public void setExamSubNumber(int examSubNumber) {
		this.examSubNumber = examSubNumber;
	}

	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClassRoom [description=" + description + ", examSubNumber=" + examSubNumber + ", getUuid()=" + getUuid()
				+ ", getAccountId()=" + getAccountId() + "]";
	}


	/** 
	 *  
	 */
	private static final long serialVersionUID = 6259060888065917342L;
}
