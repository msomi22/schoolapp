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
package ke.co.qubintel.school.server.bean.subject;

import ke.co.qubintel.school.server.bean.StorableBean;

/**
 * @author peter
 *
 */
public class Category  extends StorableBean{


	private String description;
	private int maxNo;

	/**
	 * 
	 */
	public Category() {
		description = "";
		maxNo = 0;
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
	 * @return the maxNo
	 */
	public int getMaxNo() {
		return maxNo;
	}

	/**
	 * @param maxNo the maxNo to set
	 */
	public void setMaxNo(int maxNo) {
		this.maxNo = maxNo;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [description=" + description + ", maxNo=" + maxNo + ", getUuid()=" + getUuid()
		+ ", getAccountId()=" + getAccountId() + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 4085642203319376682L;
}
