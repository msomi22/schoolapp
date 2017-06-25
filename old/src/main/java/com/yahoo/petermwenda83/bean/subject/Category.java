/**
 * 
 */
package com.yahoo.petermwenda83.bean.subject;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class Category  extends StorableBean{


	private String description;
	private String maxNo;

	/**
	 * 
	 */
	public Category() {
		description = "";
		maxNo = "";
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
	public String getMaxNo() {
		return maxNo;
	}

	/**
	 * @param maxNo the maxNo to set
	 */
	public void setMaxNo(String maxNo) {
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
