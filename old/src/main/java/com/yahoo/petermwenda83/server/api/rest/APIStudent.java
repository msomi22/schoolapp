/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import javax.xml.bind.annotation.XmlRootElement;

import com.yahoo.petermwenda83.bean.student.Student;

/**
 * @author peter
 *
 */
@XmlRootElement(name = "student") 
public class APIStudent extends Student{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public APIStudent() {
		
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "APIStudent [getRegStream()=" + getRegStream() + ", getCurrentStream()=" + getCurrentStream()
				+ ", getIsActive()=" + getIsActive() + ", getIsAlumni()=" + getIsAlumni() + ", getIsBoarding()="
				+ getIsBoarding() + ", getRegNo()=" + getRegNo() + ", getFirstname()=" + getFirstname()
				+ ", getMiddlename()=" + getMiddlename() + ", getLastname()=" + getLastname() + ", getGender()="
				+ getGender() + ", getDob()=" + getDob() + ", getBcertNo()=" + getBcertNo() + ", getCounty()="
				+ getCounty() + ", getRegTerm()=" + getRegTerm() + ", getFinalYear()=" + getFinalYear()
				+ ", getFinalTerm()=" + getFinalTerm() + ", getPassport()=" + getPassport() + ", getLastUpdated()="
				+ getLastUpdated() + ", getAdmissionDate()=" + getAdmissionDate() + ", getUuid()=" + getUuid()
				+ ", getAccountId()=" + getAccountId() + "]";
	}
	
	

}
