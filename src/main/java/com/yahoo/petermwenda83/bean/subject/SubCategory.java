/**
 * 
 */
package com.yahoo.petermwenda83.bean.subject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.student.StudentSubject;

/**
 * @author peter
 *
 */
@Entity
@Table( name = "subcategory" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class SubCategory extends StorableBeanByUUID {
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="categoryId", referencedColumnName="uuid")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="subjectId", referencedColumnName="uuid")
	private Subject subject;

	/**
	 * 
	 */
	public SubCategory() {
		account = new Account();
		category = new Category();
		subject = new Subject();
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
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		SubCategory subCategory;
		
		if(obj instanceof SubCategory) {
			subCategory = (SubCategory) obj;
			
			return getUuid().equals(subCategory.getUuid());
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
		return "SubCategory [account=" + account.getUsername() + ", category=" + category.getDescription() + ", subject=" + subject.getCode() + ", getUuid()="
				+ getUuid() + "]";
	}

}
