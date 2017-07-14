
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

package com.yahoo.petermwenda83.bean.account;

import java.sql.Timestamp;
import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * A school
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 */
public class Account extends StorableBean{
	
	private String isActive;
	private String name;
	private String motto;
	private String website;
	private String logo;
	private String signature;
	private String username ;
	private String password;
	private String mobile;
	private String email;
	private String address;
	private String town;
	private String isBoarding;
	private String isMixed;
	private String lastUpdated;
	private Timestamp creationDate;

	public Account() {
		isActive = "";
		name = "";
		motto = "";
		website = "";
		logo = "";
		signature = "";
		username = "";
		password = "";
		mobile = "";
		email = "";
		address = "";
		town = "";
		isBoarding = "";
		isMixed = "";
		lastUpdated = "";
		creationDate = new Timestamp(new Date().getTime()); 
	}
	

	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}


	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the motto
	 */
	public String getMotto() {
		return motto;
	}


	/**
	 * @param motto the motto to set
	 */
	public void setMotto(String motto) {
		this.motto = motto;
	}


	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}


	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}


	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}


	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}


	/**
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}


	/**
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}


	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * @return the town
	 */
	public String getTown() {
		return town;
	}


	/**
	 * @param town the town to set
	 */
	public void setTown(String town) {
		this.town = town;
	}


	/**
	 * @return the isBoarding
	 */
	public String getIsBoarding() {
		return isBoarding;
	}


	/**
	 * @param isBoarding the isBoarding to set
	 */
	public void setIsBoarding(String isBoarding) {
		this.isBoarding = isBoarding;
	}


	/**
	 * @return the isMixed
	 */
	public String getIsMixed() {
		return isMixed;
	}


	/**
	 * @param isMixed the isMixed to set
	 */
	public void setIsMixed(String isMixed) {
		this.isMixed = isMixed;
	}


	/**
	 * @return the lastUpdated
	 */
	public String getLastUpdated() {
		return lastUpdated;
	}


	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


	/**
	 * @return the creationDate
	 */
	public Timestamp getCreationDate() {
		return creationDate;
	}


	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [isActive=" + isActive + ", name=" + name + ", motto=" + motto + ", website=" + website
				+ ", logo=" + logo + ", signature=" + signature + ", username=" + username + ", password=" + password
				+ ", mobile=" + mobile + ", email=" + email + ", address=" + address + ", town=" + town
				+ ", isBoarding=" + isBoarding + ", isMixed=" + isMixed + ", lastUpdated=" + lastUpdated
				+ ", creationDate=" + creationDate + ", getUuid()=" + getUuid() + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = -6003538799757391780L;
}
