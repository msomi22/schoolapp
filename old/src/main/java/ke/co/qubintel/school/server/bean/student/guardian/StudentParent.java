
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
package ke.co.qubintel.school.server.bean.student.guardian;

import ke.co.qubintel.school.server.bean.StorableBean;


/**
 * Manages Student's Parent/Relative --During admission
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentParent extends StorableBean  {
	
	private String studentId;
	private String name;
	private String mobile;
	private String email;
	private String lastUpdated;
	
	
	public StudentParent() {
		super();
		studentId = "";
		name = "";
		mobile = "";
		email ="";
		lastUpdated ="";
	}
	
	
	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return studentId;
	}


	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentParent [studentId=" + studentId + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", lastUpdated=" + lastUpdated + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId()
				+ "]";
	}


	/**  
	 * 
	 */
	private static final long serialVersionUID = 5745863584453493132L;
}
