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
public class Withdraw extends PocketMoney{

	
	private Timestamp withdrawDate;
	
	/** 
	 * 
	 */
	public Withdraw() {
		super();
		withdrawDate = new Timestamp(new Date().getTime());
	}


	
	/**
	 * @return the withdrawDate
	 */
	public Timestamp getWithdrawDate() {
		return withdrawDate;
	}

	/**
	 * @param withdrawDate the withdrawDate to set
	 */
	public void setWithdrawDate(Timestamp withdrawDate) {
		this.withdrawDate = withdrawDate;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Withdraw [withdrawDate=" + withdrawDate + ", getStudentId()=" + getStudentId() + ", getAmount()="
				+ getAmount() + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}



	private static final long serialVersionUID = -7496104242563750614L;
}
