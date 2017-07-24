
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
 * Student's Primary SchoolAccount Informations
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentPrimary extends StorableBean {
	
	/**  
	 * 
	 */
	private static final long serialVersionUID = 46794661890236283L;
	private String studentUuid;
	private String schoolname;
	private String index;
	private String kcpeyear;
	private String kcpemark;
	
	/**
	 * 
	 */
	public StudentPrimary() {
		super();
		studentUuid ="";
		schoolname ="";
		index ="";
		kcpeyear ="";
		kcpemark ="";
	}

	/**
	 * @return the studentUuid
	 */
	public String getStudentUuid() {
		return studentUuid;
	}


	/**
	 * @param studentUuid the studentUuid to set
	 */
	public void setStudentUuid(String studentUuid) {
		this.studentUuid = studentUuid;
	}


	/**
	 * @return the schoolname
	 */
	public String getSchoolname() {
		return schoolname;
	}


	/**
	 * @param schoolname the schoolname to set
	 */
	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
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

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Primary Detail");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid");
		builder.append(studentUuid);
		builder.append(", schoolname =");
		builder.append(schoolname);
		builder.append(", index =");
		builder.append(index);
		builder.append(", kcpeyear =");
		builder.append(kcpeyear);
		builder.append(", kcpemark =");
		builder.append(kcpemark);
		builder.append("]");
		return builder.toString(); 
		}
}
