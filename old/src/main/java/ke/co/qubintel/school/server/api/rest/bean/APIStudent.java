/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author peter
 *
 */
@XmlRootElement(name = "student")  //only needed if we also want to generate XML     
public class APIStudent{
	
	private String regStream;
	private String currentStream;
	private String isActive;
	private String isAlumni;
	private String isBoarding;
	private String isGoKFeeEligibe;
	private String regNo;
	private String indexNo;
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
	private String admissionDate;
	
	private APIParentPrimary apiParentPrimary;
	
	
	private String uuid;
	private String accountId;

	
	public APIStudent(){
		regStream = "";
		currentStream = "";
		isActive = "1"; //active = 1, inactive = 0
		isAlumni = "0";//alumni = 1, otherwise 0
		isBoarding = "";//boarders = 1, day = 0
		isGoKFeeEligibe = "0"; // 0 = not eligibale, 1 = eligibe 
		regNo = "";
		indexNo = "";
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
		admissionDate = "";
		
		apiParentPrimary = new APIParentPrimary();
		
		
		uuid = "";
		accountId = "";
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


	public String getRegNo() {
		return regNo;
	}


	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}


	/**
	 * @return the indexNo
	 */
	public String getIndexNo() {
		return indexNo;
	}


	/**
	 * @param indexNo the indexNo to set
	 */
	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
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


	public String getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


	public String getAdmissionDate() {
		return admissionDate;
	}


	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}


	public APIParentPrimary getApiParentPrimary() {
		return apiParentPrimary;
	}


	public void setApiParentPrimary(APIParentPrimary apiParentPrimary) {
		this.apiParentPrimary = apiParentPrimary;
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


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "APIStudent [regStream=" + regStream + ", currentStream=" + currentStream + ", isActive=" + isActive
				+ ", isAlumni=" + isAlumni + ", isBoarding=" + isBoarding + ", isGoKFeeEligibe=" + isGoKFeeEligibe
				+ ", regNo=" + regNo + ", indexNo=" + indexNo + ", firstname=" + firstname + ", middlename="
				+ middlename + ", lastname=" + lastname + ", gender=" + gender + ", dob=" + dob + ", bcertNo=" + bcertNo
				+ ", county=" + county + ", regTerm=" + regTerm + ", finalYear=" + finalYear + ", finalTerm="
				+ finalTerm + ", passport=" + passport + ", lastUpdated=" + lastUpdated + ", admissionDate="
				+ admissionDate + ", apiParentPrimary=" + apiParentPrimary + ", uuid=" + uuid + ", accountId="
				+ accountId + "]";
	}



}
