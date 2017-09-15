/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */

@XmlRootElement(name = "StudentFeeAPI") 
public class StudentFeeAPI{
	
	private String regNo;
	private String firstname;
	private String middlename;
	private String lastname;
	private String stream;
	private String isBoarding;
	private String balance;
	private List<APIStudentFee> feeHistory;
	private List<APIStudentOtherFee> otherfeeHistory;
	private List<RevertedFee> revertedFeeList;
	
	public StudentFeeAPI(){
		regNo = "";
		firstname = "";
		middlename = "";
		lastname = "";
		stream = "";
		isBoarding = "";
		balance = "";
		feeHistory = new ArrayList<>();
		otherfeeHistory = new ArrayList<>();
		revertedFeeList = new ArrayList<>();
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

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getIsBoarding() {
		return isBoarding;
	}

	public void setIsBoarding(String isBoarding) {
		this.isBoarding = isBoarding;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public List<APIStudentFee> getFeeHistory() {
		return feeHistory;
	}

	public void setFeeHistory(List<APIStudentFee> feeHistory) {
		this.feeHistory = feeHistory;
	}

	public List<APIStudentOtherFee> getOtherfeeHistory() {
		return otherfeeHistory;
	}

	public void setOtherfeeHistory(List<APIStudentOtherFee> otherfeeHistory) {
		this.otherfeeHistory = otherfeeHistory;
	}

	public List<RevertedFee> getRevertedFeeList() {
		return revertedFeeList;
	}

	public void setRevertedFeeList(List<RevertedFee> revertedFeeList) {
		this.revertedFeeList = revertedFeeList;
	}

	@Override
	public String toString() {
		return "StudentFeeAPI [regNo=" + regNo + ", firstname=" + firstname + ", middlename=" + middlename
				+ ", lastname=" + lastname + ", stream=" + stream + ", isBoarding=" + isBoarding + ", balance="
				+ balance + ", feeHistory=" + feeHistory + ", otherfeeHistory=" + otherfeeHistory + ", revertedFeeList="
				+ revertedFeeList + "]";
	}

	
	
	
}