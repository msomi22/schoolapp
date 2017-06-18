
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

import java.util.Date;

/**
 * Staff Basic Informations 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StaffDetails extends Staff{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7717234136342401517L;
	
	private String staffUuid ; 
	private String employeeNo ; 
	private String firstName;
	private String lastName;
	private String surname;
	private String gender;
	private String nhifNo;
	private String nssfNo; 
	private String phone;
	private String dOB;
	private String nationalID;
	private String county;
	private String sysUser ;
	private Date registrationDate;
/**
 * 
 */
	public StaffDetails() {
		super();
		staffUuid = "";
		employeeNo ="";
		firstName ="";
		lastName ="";
		surname ="";
		gender ="";
		nhifNo ="";
		nssfNo ="";
		phone ="";
		nationalID ="";
		county ="";
		dOB ="";
		sysUser ="";
		registrationDate = new Date();
	}
 
/**
 * @return the staffUuid
 */
public String getStaffUuid() {
	return staffUuid;
}

/**
 * @param staffUuid the staffUuid to set
 */
public void setStaffUuid(String staffUuid) {
	this.staffUuid = staffUuid;
}

/**
 * @return the employeeNo
 */
public String getEmployeeNo() {
	return employeeNo;
}

/**
 * @param employeeNo the employeeNo to set
 */
public void setEmployeeNo(String employeeNo) {
	this.employeeNo = employeeNo;
}

/**
 * @return the firstName
 */
public String getFirstName() {
	return firstName;
}

/**
 * @param firstName the firstName to set
 */
public void setFirstName(String firstName) {
	this.firstName = firstName;
}

/**
 * @return the lastName
 */
public String getLastName() {
	return lastName;
}

/**
 * @param lastName the lastName to set
 */
public void setLastName(String lastName) {
	this.lastName = lastName;
}

/**
 * @return the surname
 */
public String getSurname() {
	return surname;
}

/**
 * @param surname the surname to set
 */
public void setSurname(String surname) {
	this.surname = surname;
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
 * @return the nhifNo
 */
public String getNhifNo() {
	return nhifNo;
}

/**
 * @param nhifNo the nhifNo to set
 */
public void setNhifNo(String nhifNo) {
	this.nhifNo = nhifNo;
}

/**
 * @return the nssfNo
 */
public String getNssfNo() {
	return nssfNo;
}

/**
 * @param nssfNo the nssfNo to set
 */
public void setNssfNo(String nssfNo) {
	this.nssfNo = nssfNo;
}

/**
 * @return the phone
 */
public String getPhone() {
	return phone;
}

/**
 * @param phone the phone to set
 */
public void setPhone(String phone) {
	this.phone = phone;
}

/**
 * @return the dOB
 */
public String getdOB() {
	return dOB;
}

/**
 * @param dOB the dOB to set
 */
public void setdOB(String dOB) {
	this.dOB = dOB;
}

/**
 * @return the nationalID
 */
public String getNationalID() {
	return nationalID;
}

/**
 * @param nationalID the nationalID to set
 */
public void setNationalID(String nationalID) {
	this.nationalID = nationalID;
}

/**
 * @return the county
 */
public String getCounty() {
	return county;
}

/**
 * @param county the county to set
 */
public void setCounty(String county) {
	this.county = county;
}

/**
 * @return the sysUser
 */
public String getSysUser() {
	return sysUser;
}

/**
 * @param sysUser the sysUser to set
 */
public void setSysUser(String sysUser) {
	this.sysUser = sysUser;
}

/**
 * @return the registrationDate
 */
public Date getRegistrationDate() {
	return registrationDate;
}

/**
 * @param registrationDate the registrationDate to set
 */
public void setRegistrationDate(Date registrationDate) {
	this.registrationDate = new Date(registrationDate.getTime());
}

@Override
public String toString(){
	StringBuilder builder = new StringBuilder();
	builder.append("Staff Detail [ staffUuid =");
	builder.append(staffUuid);
	builder.append(",employeeNo =");
	builder.append(	employeeNo);
	builder.append(",firstName =");
	builder.append(firstName);
	builder.append(",lastName =");
	builder.append(lastName);
	builder.append(",surname =");
	builder.append(surname);
	builder.append(", gender =");
	builder.append(gender);
	builder.append(",nhifNo =");
	builder.append(nhifNo);
	builder.append(", nssfNo =");
	builder.append(nssfNo);
	builder.append(", phone =");
	builder.append(phone);
	builder.append(", nationalID =");
	builder.append(nationalID);
	builder.append(", county =");
	builder.append(county);
	builder.append(", dOB =");
	builder.append(dOB);
	builder.append(", sysUser =");
	builder.append(sysUser);
	builder.append(", registrationDate =");
	builder.append(registrationDate);
	builder.append("]");
	return builder.toString(); 
	}
}
