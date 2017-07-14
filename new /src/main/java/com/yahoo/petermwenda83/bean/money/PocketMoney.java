/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.student.Student;

/** 
 * @author peter
 *
 */
@Entity
@Table( name = "pocketmoney" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class PocketMoney extends StorableBeanByUUID{

	private int amount;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="studentId", referencedColumnName="uuid")
	private Student student;
	
	/**
	 * 
	 */
	public PocketMoney() {
		amount = 0;
		
		account = new Account();
		student = new Student();
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
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}


	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}


	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}


	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PocketMoney [amount=" + amount + ", account=" + account + ", student=" + student + ", getUuid()="
				+ getUuid() + "]";
	}



	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
     
    
	
}
