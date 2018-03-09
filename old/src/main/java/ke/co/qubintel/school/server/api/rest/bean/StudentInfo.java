/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

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
	private String isGoKFeeEligibe;
	private int studentCount;
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
	//@JsonProperty
	private String hasParent;
	private String parentName;
	private String parentMobile;
	private String parentEmail;
	//@JsonProperty
	private String hasPrimary;
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
		isGoKFeeEligibe = "0"; // 0 = not eligibale, 1 = eligibe 
		studentCount = 0;
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
		
		hasParent = "";
		parentName = "";
		parentMobile = "";
		parentEmail ="";
		
		hasPrimary ="";
		schoolName ="";
		index ="";
		kcpeyear ="";
		kcpemark ="";
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	 * @return the studentCount
	 */
	public int getStudentCount() {
		return studentCount;
	}

	/**
	 * @param studentCount the studentCount to set
	 */
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
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
	 * @return the hasParent
	 */
	public String getHasParent() {
		return hasParent;
	}

	/**
	 * @param hasParent the hasParent to set
	 */
	public void setHasParent(String hasParent) {
		this.hasParent = hasParent;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @return the parentMobile
	 */
	public String getParentMobile() {
		return parentMobile;
	}

	/**
	 * @param parentMobile the parentMobile to set
	 */
	public void setParentMobile(String parentMobile) {
		this.parentMobile = parentMobile;
	}

	/**
	 * @return the parentEmail
	 */
	public String getParentEmail() {
		return parentEmail;
	}

	/**
	 * @param parentEmail the parentEmail to set
	 */
	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	/**
	 * @return the hasPrimary
	 */
	public String getHasPrimary() {
		return hasPrimary;
	}

	/**
	 * @param hasPrimary the hasPrimary to set
	 */
	public void setHasPrimary(String hasPrimary) {
		this.hasPrimary = hasPrimary;
	}

	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the kcpeyear
	 */
	public String getKcpeyear() {
		return kcpeyear;
	}

	/**
	 * @param kcpeyear the kcpeyear to set
	 */
	public void setKcpeyear(String kcpeyear) {
		this.kcpeyear = kcpeyear;
	}

	/**
	 * @return the kcpemark
	 */
	public String getKcpemark() {
		return kcpemark;
	}

	/**
	 * @param kcpemark the kcpemark to set
	 */
	public void setKcpemark(String kcpemark) {
		this.kcpemark = kcpemark;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentInfo [uuid=" + uuid + ", accountId=" + accountId + ", regStream=" + regStream
				+ ", currentStream=" + currentStream + ", isActive=" + isActive + ", isAlumni=" + isAlumni
				+ ", isBoarding=" + isBoarding + ", isGoKFeeEligibe=" + isGoKFeeEligibe + ", studentCount="
				+ studentCount + ", regNo=" + regNo + ", firstname=" + firstname + ", middlename=" + middlename
				+ ", lastname=" + lastname + ", gender=" + gender + ", dob=" + dob + ", bcertNo=" + bcertNo
				+ ", county=" + county + ", regTerm=" + regTerm + ", finalYear=" + finalYear + ", finalTerm="
				+ finalTerm + ", passport=" + passport + ", hasParent=" + hasParent + ", parentName=" + parentName
				+ ", parentMobile=" + parentMobile + ", parentEmail=" + parentEmail + ", hasPrimary=" + hasPrimary
				+ ", schoolName=" + schoolName + ", index=" + index + ", kcpeyear=" + kcpeyear + ", kcpemark="
				+ kcpemark + "]";
	}

}
