
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

import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * Student's House (Dormitory) 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentHouse extends StorableBean  {
	
	/** 
	 * 
	 */
	private static final long serialVersionUID = -4883618114808381680L;
	private String studentUuid;
	private String HouseUuid;
	private String sysUser;
	private Date dateIn;
	/**
	 * 
	 */
	public StudentHouse() {
		super();
		studentUuid = "";
		HouseUuid = "";
		sysUser = "";
		dateIn  = new Date();//
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
	 * @return the houseUuid
	 */
	public String getHouseUuid() {
		return HouseUuid;
	}



	/**
	 * @param houseUuid the houseUuid to set
	 */
	public void setHouseUuid(String houseUuid) {
		HouseUuid = houseUuid;
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
	 * @return the dateIn
	 */
	public Date getDateIn() {
		return dateIn;
	}



	/**
	 * @param dateIn the dateIn to set
	 */
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}



	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Student's House,");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid = ");
		builder.append(studentUuid);
		builder.append(", HouseUuid = ");
		builder.append(HouseUuid);
		builder.append(", sysUser = ");
		builder.append(sysUser);
		builder.append(", dateIn = ");
		builder.append(dateIn);
		builder.append("]");
		return builder.toString(); 
		}


}
