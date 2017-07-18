
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

import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * Teacher Subject-ClassRoom Allocation
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TeacherSubClass extends StorableBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8748801928170289528L;
	private String teacherUuid;
	private String subjectUuid;
	private String classRoomUuid;
	private String sysUser;
	private Date allocationDate;
	
	/**
	 * 
	 */
	public TeacherSubClass() {
		super();
		teacherUuid ="";
		subjectUuid ="";
		classRoomUuid ="";
		sysUser ="";
		allocationDate = new Date(); 
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
	 * @return the subjectUuid
	 */
	public String getSubjectUuid() {
		return subjectUuid;
	}



	/**
	 * @param subjectUuid the subjectUuid to set
	 */
	public void setSubjectUuid(String subjectUuid) {
		this.subjectUuid = subjectUuid;
	}



	/**
	 * @return the classRoomUuid
	 */
	public String getClassRoomUuid() {
		return classRoomUuid;
	}



	/**
	 * @param classRoomUuid the classRoomUuid to set
	 */
	public void setClassRoomUuid(String classRoomUuid) {
		this.classRoomUuid = classRoomUuid;
	}



	/**
	 * @return the sysUser
	 */
	public String getSysUser() {
		return sysUser;
	}



	/**
	 * @param sysUser the sysUser to set
	 */
	public void setSysUser(String sysUser) {
		this.sysUser = sysUser;
	}



	/**
	 * @return the allocationDate
	 */
	public Date getAllocationDate() {
		return allocationDate;
	}



	/**
	 * @param allocationDate the allocationDate to set
	 */
	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = new Date(allocationDate.getTime());
	}



	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Teacher's SUbject-ClassRoom");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",teacherUuid=");
		builder.append(teacherUuid);
		builder.append(",subjectUuid=");
		builder.append(subjectUuid);
		builder.append(", classRoomUuid=");
		builder.append(classRoomUuid);
		builder.append(", sysUser=");
		builder.append(sysUser);
		builder.append(",allocationDate=");
		builder.append(allocationDate);
		builder.append("]");
		return builder.toString(); 
		}
}
