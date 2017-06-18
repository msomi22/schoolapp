
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
package com.yahoo.petermwenda83.bean.schoolaccount;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 *  A resource, SchoolAccount Logo and Digital Signature
 *  
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Resources extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6715493131557119146L;
	private String schoolAccountUuid;
	private String logo;
	private String digitalSignature;

	/**
	 * 
	 */
	public Resources() {
		    schoolAccountUuid ="";
		    logo ="";
		    digitalSignature ="";
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
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the digitalSignature
	 */
	public String getDigitalSignature() {
		return digitalSignature;
	}

	/**
	 * @param digitalSignature the digitalSignature to set
	 */
	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Resource");
		builder.append("[getUuid()=");
		builder.append(getUuid()); 
		builder.append(",schoolAccountUuid=");
		builder.append(schoolAccountUuid);
		builder.append(",logo=");
		builder.append(logo);
		builder.append(", digitalSignature=");
		builder.append(digitalSignature);
		builder.append("]");
		return builder.toString(); 
		}
}
