/**
 * 
 */
package com.yahoo.petermwenda83.bean.schoolaccount;

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
	private String schoolAccountUuid;
	private String key;
	private String value;

	/**
	 * 
	 */
	public Miscellanous() {
		schoolAccountUuid = "";
		key = ""; 
		value = "";
	}
	
	/**
	 * @return the schoolAccountUuid
	 */
	public String getSchoolAccountUuid() {
		return schoolAccountUuid;
	}

	/**
	 * @param schoolAccountUuid the schoolAccountUuid to set
	 */
	public void setSchoolAccountUuid(String schoolAccountUuid) {
		this.schoolAccountUuid = schoolAccountUuid;
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
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Miscellanous");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",schoolAccountUuid=");
		builder.append(schoolAccountUuid);
		builder.append(",key=");
		builder.append(key);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString(); 
		}
}
