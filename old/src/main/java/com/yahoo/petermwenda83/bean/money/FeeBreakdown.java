/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class FeeBreakdown extends StorableBean{

	
	private String feeCategory;
	private String term;
	private String year;
	private String status;

	/**
	 * 
	 */
	public FeeBreakdown() {
		feeCategory = "";
		term = "";
		status = "";
	}

	

	public String getFeeCategory() {
		return feeCategory;
	}



	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}



	public String getTerm() {
		return term;
	}



	public void setTerm(String term) {
		this.term = term;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	@Override
	public String toString() {
		return "FeeBreakdown [feeCategory=" + feeCategory + ", term=" + term + ", year=" + year + ", status=" + status
				+ "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -1571034939933518933L;

}
