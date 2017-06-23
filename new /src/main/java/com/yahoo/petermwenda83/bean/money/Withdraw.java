
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

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** 
 * @author peter
 *
 */
@Entity
@Table( name = "withdraw" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
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





	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Withdraw [withdrawDate=" + withdrawDate + ", getAmount()=" + getAmount() + ", getAccount()="
				+ getAccount().getUsername() + ", getStudent()=" + getStudent().getRegNo() + ", getUuid()=" + getUuid() + "]";
	}





	private static final long serialVersionUID = -7496104242563750614L;
}
