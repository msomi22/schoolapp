
package com.yahoo.petermwenda83.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * An object that can be persisted with its primary key as an integer (id).
 * <p>
 *  
 */
@MappedSuperclass
public class StorableBeanById extends StorableBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	/**
	 * 
	 */
	public StorableBeanById() {
		id = 0;
	}
	
	
	/**
	 * @return the id
	 */
	
	public long getId() {
		return id;
	}
	
	
	/**
	 * @param id - the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7578549306872629910L;
}
