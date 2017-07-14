/**
 * 
 */
package com.yahoo.petermwenda83.bean.subject;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class SubCategory  extends StorableBean{

	private String categoryId;
	private String subjectId;

	/**
	 * 
	 */
	public SubCategory() {
		categoryId = "";
		subjectId = "";
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubCategory [categoryId=" + categoryId + ", subjectId=" + subjectId + ", getUuid()=" + getUuid()
		+ ", getAccountId()=" + getAccountId() + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4085642203319376682L;


}
