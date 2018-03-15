/**
 * 
 */
package ke.co.qubintel.school.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class APIParentPrimary {
	
	//parent
		private String parentName;
		private String parentMobile;
		private String parentEmail;
		
		//primary
		private String schoolName;
		private String index;
		private String kcpeyear;
		private String kcpemark;
		private String kcpeGrade;

	/**
	 * 
	 */
	public APIParentPrimary() {
		parentName = "";
		parentMobile = "";
		parentEmail ="";
		
		schoolName ="";
		index ="";
		kcpeyear ="";
		kcpemark ="";
		kcpeGrade ="";
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

	/**
	 * @return the kcpeGrade
	 */
	public String getKcpeGrade() {
		return kcpeGrade;
	}

	/**
	 * @param kcpeGrade the kcpeGrade to set
	 */
	public void setKcpeGrade(String kcpeGrade) {
		this.kcpeGrade = kcpeGrade;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "APIParentPrimary [parentName=" + parentName + ", parentMobile=" + parentMobile + ", parentEmail="
				+ parentEmail + ", schoolName=" + schoolName + ", index=" + index + ", kcpeyear=" + kcpeyear
				+ ", kcpemark=" + kcpemark + ", kcpeGrade=" + kcpeGrade + "]";
	}

}
