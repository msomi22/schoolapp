/**
 * 
 */
package com.yahoo.petermwenda83.bean.otherfee;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.money.StudentFee;

/** 
 * @author peter
 *
 */
@Entity
@Table( name = "suspense" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Suspense extends StudentFee{

	
	public Suspense() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Suspense [getAmountPaid()=" + getAmountPaid() + ", getPayMode()=" + getPayMode()
				+ ", getTransactionId()=" + getTransactionId() + ", getPaidHas()=" + getPaidHas() + ", getDatePaid()="
				+ getDatePaid() + ", getAccount()=" + getAccount().getUsername() + ", getStudent()=" + getStudent().getRegNo() + ", getUuid()="
				+ getUuid() + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1218441050635412235L;

}
