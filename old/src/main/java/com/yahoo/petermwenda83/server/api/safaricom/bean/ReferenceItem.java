/**
 * 
 */
package com.yahoo.petermwenda83.server.api.safaricom.bean;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author peter
 *
 */

//@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "ReferenceItem") 
public class ReferenceItem {
	
	@JsonProperty
	private String Key;
	@JsonProperty
	private String Value;
	
	/**
	 * 
	 */
	public ReferenceItem() {
		Key = "";
		Value = "";
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	
	@Override
	public String toString() {
		return "ReferenceItem [Key=" + Key + ", Value=" + Value + "]";
	}
	

}
