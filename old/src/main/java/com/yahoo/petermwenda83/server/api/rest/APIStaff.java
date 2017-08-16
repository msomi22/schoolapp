package com.yahoo.petermwenda83.server.api.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author peter
 *
 */
@XmlRootElement(name = "staff")  //only needed if we also want to generate XML     
public class APIStaff{
	
	private String acessLevelId; 
	private String staffNo; 
	private String firstname;
	private String middlename;
	private String lastname;
	private String gender;
	private String mobile;
	private String email; 
	private String username;
	private String password;

	public APIStaff() {
		acessLevelId = "";
		staffNo ="";
		firstname ="";
		middlename ="";
		lastname ="";
		gender ="";
		mobile ="";
		email ="";
		username ="";
		password ="";
	}

	
	/**
	 * @return the acessLevelId
	 */
	public String getAcessLevelId() {
		return acessLevelId;
	}


	/**
	 * @param acessLevelId the acessLevelId to set
	 */
	public void setAcessLevelId(String acessLevelId) {
		this.acessLevelId = acessLevelId;
	}


	/**
	 * @return the staffNo
	 */
	public String getStaffNo() {
		return staffNo;
	}


	/**
	 * @param staffNo the staffNo to set
	 */
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}


	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}


	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	/**
	 * @return the middlename
	 */
	public String getMiddlename() {
		return middlename;
	}


	/**
	 * @param middlename the middlename to set
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}


	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}


	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}


	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "APIStaff [acessLevelId=" + acessLevelId + ", staffNo=" + staffNo + ", firstname=" + firstname
				+ ", middlename=" + middlename + ", lastname=" + lastname + ", gender=" + gender + ", mobile=" + mobile
				+ ", email=" + email + ", username=" + username + ", password=" + password + "]";
	}


	
}
