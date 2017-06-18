
/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package com.yahoo.petermwenda83.bean.money;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author peter
 *
 */
public class Deposit extends PocketMoney {
	
	
	private Timestamp depositDate;
	
	public Deposit() {
		super();
		depositDate = new Timestamp(new Date().getTime());
	}

	

	/**
	 * @return the depositDate
	 */
	public Timestamp getDepositDate() {
		return depositDate;
	}



	/**
	 * @param depositDate the depositDate to set
	 */
	public void setDepositDate(Timestamp depositDate) {
		this.depositDate = depositDate;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Deposit [depositDate=" + depositDate + ", getStudentId()=" + getStudentId() + ", getAmount()="
				+ getAmount() + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}



	/** 
	 * 
	 */
	private static final long serialVersionUID = 7582945268897264721L;
}
