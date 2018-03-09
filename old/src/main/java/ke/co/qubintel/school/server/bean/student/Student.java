
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
package ke.co.qubintel.school.server.bean.student;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.bean.StorableBean;

/** 
 * Has Student;s Basic details 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 * 
 */

public class Student extends StorableBean implements Comparable<Student> {
	
		private String regStream;
		private String currentStream;
		private String isActive;
		private String isAlumni;
		private String isBoarding;
		private String isGoKFeeEligibe;
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
		private String lastUpdated;
		private Timestamp admissionDate;
		
  
	
    public Student() {
		super();
		regStream = "";
		currentStream = "";
		isActive = "1"; //active = 1, inactive = 0
		isAlumni = "0";//alumni = 1, otherwise 0
		isBoarding = "";//boarders = 1, day = 0
		isGoKFeeEligibe = "0"; // 0 = not eligibale, 1 = eligibe 
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
		lastUpdated = "";
		admissionDate = new Timestamp(new Date().getTime());
	
	}
	
    
    /**
	 * @return the regStream
	 */
	public String getRegStream() {
		return regStream;
	}


	/**
	 * @param regStream the regStream to set
	 */
	public void setRegStream(String regStream) {
		this.regStream = regStream;
	}


	/**
	 * @return the currentStream
	 */
	public String getCurrentStream() {
		return currentStream;
	}


	/**
	 * @param currentStream the currentStream to set
	 */
	public void setCurrentStream(String currentStream) {
		this.currentStream = currentStream;
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
	 * @return the isAlumni
	 */
	public String getIsAlumni() {
		return isAlumni;
	}


	/**
	 * @param isAlumni the isAlumni to set
	 */
	public void setIsAlumni(String isAlumni) {
		this.isAlumni = isAlumni;
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
	 * @return the isGoKFeeEligibe
	 */
	public String getIsGoKFeeEligibe() {
		return isGoKFeeEligibe;
	}


	/**
	 * @param isGoKFeeEligibe the isGoKFeeEligibe to set
	 */
	public void setIsGoKFeeEligibe(String isGoKFeeEligibe) {
		this.isGoKFeeEligibe = isGoKFeeEligibe;
	}


	/**
	 * @return the regNo
	 */
	public String getRegNo() {
		return regNo;
	}


	/**
	 * @param regNo the regNo to set
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}


	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return StringUtils.capitalize(firstname.toLowerCase());
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
		return StringUtils.capitalize(middlename.toLowerCase());
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
		return StringUtils.capitalize(lastname.toLowerCase()); 
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
		return gender.toUpperCase();
	}


	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}


	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}


	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}


	/**
	 * @return the bcertNo
	 */
	public String getBcertNo() {
		return bcertNo;
	}


	/**
	 * @param bcertNo the bcertNo to set
	 */
	public void setBcertNo(String bcertNo) {
		this.bcertNo = bcertNo;
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
	 * @return the regTerm
	 */
	public String getRegTerm() {
		return regTerm;
	}


	/**
	 * @param regTerm the regTerm to set
	 */
	public void setRegTerm(String regTerm) {
		this.regTerm = regTerm;
	}


	/**
	 * @return the finalYear
	 */
	public int getFinalYear() {
		return finalYear;
	}


	/**
	 * @param finalYear the finalYear to set
	 */
	public void setFinalYear(int finalYear) {
		this.finalYear = finalYear;
	}


	/**
	 * @return the finalTerm
	 */
	public int getFinalTerm() {
		return finalTerm;
	}


	/**
	 * @param finalTerm the finalTerm to set
	 */
	public void setFinalTerm(int finalTerm) {
		this.finalTerm = finalTerm;
	}


	/**
	 * @return the passport
	 */
	public String getPassport() {
		return passport;
	}


	/**
	 * @param passport the passport to set
	 */
	public void setPassport(String passport) {
		this.passport = passport;
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
	 * @return the admissionDate
	 */
	public Timestamp getAdmissionDate() {
		return admissionDate;
	}


	/**
	 * @param admissionDate the admissionDate to set
	 */
	public void setAdmissionDate(Timestamp admissionDate) {
		this.admissionDate = admissionDate;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Student [regStream=" + regStream + ", currentStream=" + currentStream + ", isActive=" + isActive
				+ ", isAlumni=" + isAlumni + ", isBoarding=" + isBoarding + ", isGoKFeeEligibe=" + isGoKFeeEligibe
				+ ", regNo=" + regNo + ", firstname=" + firstname + ", middlename=" + middlename + ", lastname="
				+ lastname + ", gender=" + gender + ", dob=" + dob + ", bcertNo=" + bcertNo + ", county=" + county
				+ ", regTerm=" + regTerm + ", finalYear=" + finalYear + ", finalTerm=" + finalTerm + ", passport="
				+ passport + ", lastUpdated=" + lastUpdated + ", admissionDate=" + admissionDate + ", getUuid()="
				+ getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}


	@Override
	public int compareTo(Student ss) {
		if(ss == null) {
			ss = new Student();
		}
		return getRegNo().compareTo(((Student) ss).getRegNo()); 
	}


	private static final long serialVersionUID = -7544635242162355411L;

}
