/**
 * 
 */
package com.yahoo.petermwenda83.bean.staff;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.classroom.Stream;

/** 
 * @author peter
 *  
 */

@Entity
@Table( name = "classteacher" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ClassTeacher extends StorableBeanByUUID{
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="teacherId", referencedColumnName="uuid")
	private Staff staff;
	
	@ManyToOne
	@JoinColumn(name="streamId", referencedColumnName="uuid")
	private Stream stream;
	
	/**
	 * 
	 */
	public ClassTeacher() {
		super();
		account = new Account();
		staff = new Staff();
		stream = new Stream();
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
	 * @return the staff
	 */
	public Staff getStaff() {
		return staff;
	}



	/**
	 * @param staff the staff to set
	 */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}



	/**
	 * @return the stream
	 */
	public Stream getStream() {
		return stream;
	}



	/**
	 * @param stream the stream to set
	 */
	public void setStream(Stream stream) {
		this.stream = stream;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		ClassTeacher classTeacher;
		
		if(obj instanceof ClassTeacher) {
			classTeacher = (ClassTeacher) obj;
			
			return getUuid().equals(classTeacher.getUuid());
		}
		
		return false;
	}


	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getUuid().hashCode();
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClassTeacher [account=" + account.getUsername() + ", staff=" + staff.getUsername() + ", stream=" + stream.getDescription() + ", getUuid()="
				+ getUuid() + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -720546801232129197L;
}
