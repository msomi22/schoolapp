
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
package com.yahoo.petermwenda83.bean.staff;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;

/** 
 * A staff Has A position , Either a Principal ,Deputy, Hod, Teacher , ...
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
@Entity
@Table( name = "acesslevel" )
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class AcessLevel extends StorableBeanByUUID{
	
	private String  description;
	    
	public AcessLevel() {
		description ="";
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
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {        
        boolean isEqual = false;
		
		if(obj instanceof AcessLevel) {	
			AcessLevel type = (AcessLevel)obj;
			
			isEqual = type.getUuid().equals(getUuid());		
		}
		
		return isEqual;
	}
	
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getUuid().hashCode();
	}


	
	

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("AcessLevel");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",description=");
		builder.append(description);
		return builder.toString(); 
		}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -920169356050037976L;
}
