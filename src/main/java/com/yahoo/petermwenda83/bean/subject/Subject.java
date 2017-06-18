
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
package com.yahoo.petermwenda83.bean.subject;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;
import com.yahoo.petermwenda83.bean.account.Account;

/**
 * A subject in a Account
 * 
 *  @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 * 
 */
@Entity
@Table( name = "subject" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Subject extends StorableBeanByUUID {
	
	  private String code;
	  private String numericCode;
	  private String description;
	  

		@ManyToOne
		@JoinColumn(name="accountId", referencedColumnName="uuid")
		private Account account;
		
		@ManyToOne
		@JoinColumn(name="categoryId", referencedColumnName="uuid")
		private Category category;
		

	/**
	 * 
	 */
	public Subject() {
		super();
		code = "";
		numericCode = "";
		description = "";
		
		account = new Account();
		category = new Category();
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the numericCode
	 */
	public String getNumericCode() {
		return numericCode;
	}


	/**
	 * @param numericCode the numericCode to set
	 */
	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		Subject subject;
		
		if(obj instanceof Subject) {
			subject = (Subject) obj;
			
			return getUuid().equals(subject.getUuid());
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
		return "Subject [code=" + code + ", numericCode=" + numericCode + ", description=" + description + ", account="
				+ account + ", category=" + category + ", getUuid()=" + getUuid() + "]";
	}




	private static final long serialVersionUID = 1L;

}
