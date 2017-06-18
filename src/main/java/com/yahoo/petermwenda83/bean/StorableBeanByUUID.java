
package com.yahoo.petermwenda83.bean;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * An object that can be persisted with its primary key as an uuid.
 * <p>
 * 
 *
 */
@MappedSuperclass
public class StorableBeanByUUID extends StorableBean {

	@Id
	@Column(name = "uuid", unique = true)
    @NotEmpty
	private String uuid;
        
	
	/**
	 * 
	 */
	public StorableBeanByUUID() {
		uuid = UUID.randomUUID().toString();
	}
		
	
	/**
	 * @return the uuid
	 */
	
	public String getUuid() {
		return uuid;
	}
	
	
	/**
	 * @param uuid - the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6321411695612782172L;
}
