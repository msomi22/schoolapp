/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class PocketMoney extends StorableBean{
	
	
	private String studentId;
	private int amount;
	
	/**
	 * 
	 */
	protected PocketMoney() {
		studentId = "";
		amount = 0;
	}
	
	
	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return studentId;
	}


	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}


	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PocketMoney [studentId=" + studentId + ", amount=" + amount + ", getUuid()=" + getUuid()
				+ ", getAccountId()=" + getAccountId() + "]";
	}


	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
     
    
	
}
