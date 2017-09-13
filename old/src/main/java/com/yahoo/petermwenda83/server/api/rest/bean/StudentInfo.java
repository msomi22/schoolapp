/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */
@XmlRootElement(name = "StudentInfo")
public class StudentInfo {
	
	private String uuid;
	private String accountId;
	private String regStream;
	private String currentStream;
	private String isActive;
	private String isAlumni;
	private String isBoarding;
	private String regNo;
	private String firstname;
	private String middlename;
	private String lastname;
	private String gender;
	private String dob;
	private String bcertNo;
	private String county;
	private String regTerm;
	private int finalYear;
	private int finalTerm;
	private String passport;
	
	private boolean hasParent;
	private String parentName;
	private String parentMobile;
	private String parentEmail;
	
	private boolean hasPrimary;
	private String schoolName;
	private String index;
	private String kcpeyear;
	private String kcpemark;

	/**
	 * 
	 */
	public StudentInfo() {
		uuid = "";
		accountId = "";
		regStream = "";
		currentStream = "";
		isActive = "1"; //active = 1, inactive = 0
		isAlumni = "0";//alumni = 1, otherwise 0
		isBoarding = "";//boarders = 1, day = 0
		regNo = "";
		firstname = "";
		middlename = "";
		lastname = "";
		gender = "";
		dob = "";
		bcertNo = "";
		county = "";
		regTerm = "";
		finalYear = 0;
		finalTerm = 3;
		passport = "";
		
		hasParent = false;
		parentName = "";
		parentMobile = "";
		parentEmail ="";
		
		hasPrimary =false;
		schoolName ="";
		index ="";
		kcpeyear ="";
		kcpemark ="";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getRegStream() {
		return regStream;
	}

	public void setRegStream(String regStream) {
		this.regStream = regStream;
	}

	public String getCurrentStream() {
		return currentStream;
	}

	public void setCurrentStream(String currentStream) {
		this.currentStream = currentStream;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsAlumni() {
		return isAlumni;
	}

	public void setIsAlumni(String isAlumni) {
		this.isAlumni = isAlumni;
	}

	public String getIsBoarding() {
		return isBoarding;
	}

	public void setIsBoarding(String isBoarding) {
		this.isBoarding = isBoarding;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getBcertNo() {
		return bcertNo;
	}

	public void setBcertNo(String bcertNo) {
		this.bcertNo = bcertNo;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getRegTerm() {
		return regTerm;
	}

	public void setRegTerm(String regTerm) {
		this.regTerm = regTerm;
	}

	public int getFinalYear() {
		return finalYear;
	}

	public void setFinalYear(int finalYear) {
		this.finalYear = finalYear;
	}

	public int getFinalTerm() {
		return finalTerm;
	}

	public void setFinalTerm(int finalTerm) {
		this.finalTerm = finalTerm;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public boolean hasParent() {
		return hasParent;
	}

	public void setHasParent(boolean hasParent) {
		this.hasParent = hasParent;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentMobile() {
		return parentMobile;
	}

	public void setParentMobile(String parentMobile) {
		this.parentMobile = parentMobile;
	}

	public String getParentEmail() {
		return parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	public boolean hasPrimary() {
		return hasPrimary;
	}

	public void setHasPrimary(boolean hasPrimary) {
		this.hasPrimary = hasPrimary;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getKcpeyear() {
		return kcpeyear;
	}

	public void setKcpeyear(String kcpeyear) {
		this.kcpeyear = kcpeyear;
	}

	public String getKcpemark() {
		return kcpemark;
	}

	public void setKcpemark(String kcpemark) {
		this.kcpemark = kcpemark;
	}

	@Override
	public String toString() {
		return "StudentInfo [uuid=" + uuid + ", accountId=" + accountId + ", regStream=" + regStream
				+ ", currentStream=" + currentStream + ", isActive=" + isActive + ", isAlumni=" + isAlumni
				+ ", isBoarding=" + isBoarding + ", regNo=" + regNo + ", firstname=" + firstname + ", middlename="
				+ middlename + ", lastname=" + lastname + ", gender=" + gender + ", dob=" + dob + ", bcertNo=" + bcertNo
				+ ", county=" + county + ", regTerm=" + regTerm + ", finalYear=" + finalYear + ", finalTerm="
				+ finalTerm + ", passport=" + passport + ", hasParent=" + hasParent + ", parentName=" + parentName
				+ ", parentMobile=" + parentMobile + ", parentEmail=" + parentEmail + ", hasPrimary=" + hasPrimary
				+ ", schoolName=" + schoolName + ", index=" + index + ", kcpeyear=" + kcpeyear + ", kcpemark="
				+ kcpemark + "]";
	}

}
