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
package ke.co.qubintel.school.server.bean.money;

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
