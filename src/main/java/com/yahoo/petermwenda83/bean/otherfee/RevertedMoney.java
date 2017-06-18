/**
 * 
 */
package com.yahoo.petermwenda83.bean.otherfee;

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
@Table( name = "revertedmoney" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class RevertedMoney extends StudentOtherFee{
	
	private Timestamp dateReverted;
	

	/**
	 * 
	 */
	public RevertedMoney() {
		dateReverted = new Timestamp(new Date().getTime());
	}
    
	
	/**
	 * @return the dateReverted
	 */
	public Timestamp getDateReverted() {
		return dateReverted;
	}


	/**
	 * @param dateReverted the dateReverted to set
	 */
	public void setDateReverted(Timestamp dateReverted) {
		this.dateReverted = dateReverted;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RevertedMoney [dateReverted=" + dateReverted + ", getAccount()=" + getAccount().getUsername() + ", getStudent()="
				+ getStudent() + ", getOtherFee()=" + getOtherFee().getUuid() + ", getUuid()=" + getUuid() + "]";
	}


	/**
		 * 
		 */
		private static final long serialVersionUID = 4675525343037574737L;
}
