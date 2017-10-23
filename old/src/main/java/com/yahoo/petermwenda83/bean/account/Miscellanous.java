/**
 * 
 */
package com.yahoo.petermwenda83.bean.account;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
/**
 * @author peter
 *
 */
public class Miscellanous extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5808103297941824732L;
	
	private String key;
	private String value;

	/**
	 * 
	 */
	public Miscellanous() {
		key = ""; 
		value = "";
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value.substring(0, Math.min(value.length(), 110)).toLowerCase(); 
	}
	
	public String getFullValue() {
		return value; 
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Miscellanous [key=" + key + ", value=" + value + ", getUuid()=" + getUuid() + ", getAccountId()="
				+ getAccountId() + "]";
	}

	
}
