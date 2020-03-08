
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

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.bean.StorableBean;

/**
 * Student's Primary Account Informations
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentPrimary extends StorableBean {
	
	private String studentId;
	private String schoolName;
	private String index;
	private String kcpeyear;
	private String kcpemark;
	private String kcpeGrade;
	
	
	/**
	 * 
	 */
	public StudentPrimary() {
		super();
		studentId ="";
		schoolName ="";
		index ="";
		kcpeyear ="";
		kcpemark = "0";
		kcpeGrade = "";
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
		if(StringUtils.isEmpty(kcpemark)) {
			return "0";
		}
		return kcpemark;
	}



	/**
	 * @param kcpemark the kcpemark to set
	 */
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
		return "StudentPrimary [studentId=" + studentId + ", schoolName=" + schoolName + ", index=" + index
				+ ", kcpeyear=" + kcpeyear + ", kcpemark=" + kcpemark + ", kcpeGrade=" + kcpeGrade + ", getUuid()="
				+ getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}



	/**  
	 * 
	 */
	private static final long serialVersionUID = 46794661890236283L;
}
