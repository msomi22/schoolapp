
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

import java.sql.Timestamp;
import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * Staff Basic Informations 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Staff extends StorableBean{

	private String acessLevelId; 
	private String staffNo; 
	private String isActive; 
	private String firstname;
	private String middlename;
	private String lastname;
	private String gender;
	private String mobile;
	private String email; 
	private String username;
	private String password;
	private Timestamp regDate;

	/**
	 * 
	 */
	public Staff() {
		super();
		acessLevelId = "";
		staffNo ="";
		isActive ="";
		firstname ="";
		middlename ="";
		lastname ="";
		gender ="";
		mobile ="";
		email ="";
		username ="";
		password ="";
		regDate = new Timestamp(new Date().getTime());
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
	 * @return the regDate
	 */
	public Timestamp getRegDate() {
		return regDate;
	}


	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Staff [acessLevelId=" + acessLevelId + ", staffNo=" + staffNo + ", isActive=" + isActive
				+ ", firstname=" + firstname + ", middlename=" + middlename + ", lastname=" + lastname + ", gender="
				+ gender + ", mobile=" + mobile + ", email=" + email + ", username=" + username + ", password="
				+ password + ", regDate=" + regDate + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId()
				+ "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 7717234136342401517L;

}
