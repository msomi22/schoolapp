
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
package com.yahoo.petermwenda83.bean.student;

import com.yahoo.petermwenda83.bean.StorableBean;

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
	
	
	/**
	 * 
	 */
	public StudentPrimary() {
		super();
		studentId ="";
		schoolName ="";
		index ="";
		kcpeyear ="";
		kcpemark ="";
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
		return "StudentPrimary [studentId=" + studentId + ", schoolName=" + schoolName + ", index=" + index
				+ ", kcpeyear=" + kcpeyear + ", kcpemark=" + kcpemark + ", getUuid()=" + getUuid() + ", getAccountId()="
				+ getAccountId() + "]";
	}



	/**  
	 * 
	 */
	private static final long serialVersionUID = 46794661890236283L;
}
