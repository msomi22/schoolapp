
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
package com.yahoo.petermwenda83.bean.staff;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * Allocate department to a staff
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TeacherDepartment extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1628158762078820579L;
	private String teacherUuid;
	private String departmentUuid;
	/**
	 * 
	 */
	public TeacherDepartment() {
		teacherUuid ="";
		departmentUuid ="";
	}
	
	/**
	 * @return the teacherUuid
	 */
	public String getTeacherUuid() {
		return teacherUuid;
	}

	/**
	 * @param teacherUuid the teacherUuid to set
	 */
	public void setTeacherUuid(String teacherUuid) {
		this.teacherUuid = teacherUuid;
	}

	/**
	 * @return the departmentUuid
	 */
	public String getDepartmentUuid() {
		return departmentUuid;
	}

	/**
	 * @param departmentUuid the departmentUuid to set
	 */
	public void setDepartmentUuid(String departmentUuid) {
		this.departmentUuid = departmentUuid;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Teacher's Department");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",teacherUuid=");
		builder.append(teacherUuid);
		builder.append(",departmentUuid=");
		builder.append(departmentUuid);
		return builder.toString(); 
		}
}
