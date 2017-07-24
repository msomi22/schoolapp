
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
 * @author peter
 *
 */
public class Staff extends StorableBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5760553114204722579L;
	private String schoolAccountUuid;
	private String statusUuid;
	private String category; 
	private String positionUuid; 
	private String userName;
	private String password;

	

	/**
	 * 
	 */
	public Staff() {
		super();
		schoolAccountUuid ="";
		statusUuid ="";
		category="";
		positionUuid="";
		userName="";
		password="";
	}
	

	/**
	 * @return the schoolAccountUuid
	 */
	public String getSchoolAccountUuid() {
		return schoolAccountUuid;
	}


	/**
	 * @param schoolAccountUuid the schoolAccountUuid to set
	 */
	public void setSchoolAccountUuid(String schoolAccountUuid) {
		this.schoolAccountUuid = schoolAccountUuid;
	}

    
	/**
	 * @return the statusUuid
	 */
	public String getStatusUuid() {
		return statusUuid;
	}


	/**
	 * @param statusUuid the statusUuid to set
	 */
	public void setStatusUuid(String statusUuid) {
		this.statusUuid = statusUuid;
	}


	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}


	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}


	/**
	 * @return the positionUuid
	 */
	public String getPositionUuid() {
		return positionUuid;
	}


	/**
	 * @param positionUuid the positionUuid to set
	 */
	public void setPositionUuid(String positionUuid) {
		this.positionUuid = positionUuid;
	}


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Staff [ getUuid() =");
		builder.append(getUuid());
		builder.append(", schoolAccountUuid =");
		builder.append(schoolAccountUuid);
		builder.append(",statusUuid=");
		builder.append(statusUuid);
		builder.append(", category =");
		builder.append(category);
		builder.append(", positionUuid =");
		builder.append(positionUuid);
		builder.append(", userName =");
		builder.append(userName);
		builder.append(", password =");
		builder.append(password);
		builder.append("]");
		return builder.toString(); 
		}

}
