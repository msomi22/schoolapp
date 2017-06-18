
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
 * Teacher Has Duties
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class TeacherDuties extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3801149462925139460L;
	private String teacherUuid;
	private String dutyUuid;
	private String sysUser;
	private Date  allocationDate;

	/**
	 * 
	 */
	public TeacherDuties() {
		teacherUuid ="";
		dutyUuid ="";
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
	 * @return the dutyUuid
	 */
	public String getDutyUuid() {
		return dutyUuid;
	}

	/**
	 * @param dutyUuid the dutyUuid to set
	 */
	public void setDutyUuid(String dutyUuid) {
		this.dutyUuid = dutyUuid;
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
		builder.append("Teacher's Duties");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",teacherUuid=");
		builder.append(teacherUuid);
		builder.append(",dutyUuid=");
		builder.append(dutyUuid);
		builder.append(", sysUser=");
		builder.append(sysUser);
		builder.append(",allocationDate=");
		builder.append(allocationDate);
		builder.append("]");
		return builder.toString(); 
		}
}
