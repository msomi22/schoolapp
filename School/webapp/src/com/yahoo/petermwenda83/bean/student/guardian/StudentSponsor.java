
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
package com.yahoo.petermwenda83.bean.student.guardian;

import com.yahoo.petermwenda83.bean.StorableBean;
/**
 * A student can have a sponsor
 * 
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentSponsor extends StorableBean  {
	
	/** 
	 * 
	 */
	private static final long serialVersionUID = -6880639740535565862L;
	private String studentUuid;
	private String SponsorName;
	private String SponsorOccupation;
	private String SponsorPhone;
	private String SponsorCountry;
	private String SponsorCounty;
	
	public StudentSponsor(){
		super();
		 studentUuid = "";
		 SponsorName = "";
		 SponsorOccupation = "";
		 SponsorPhone = "";
		 SponsorCountry = "";
		 SponsorCounty = "";
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
	 * @return the sponsorName
	 */
	public String getSponsorName() {
		return SponsorName;
	}



	/**
	 * @param sponsorName the sponsorName to set
	 */
	public void setSponsorName(String sponsorName) {
		SponsorName = sponsorName;
	}



	/**
	 * @return the sponsorOccupation
	 */
	public String getSponsorOccupation() {
		return SponsorOccupation;
	}



	/**
	 * @param sponsorOccupation the sponsorOccupation to set
	 */
	public void setSponsorOccupation(String sponsorOccupation) {
		SponsorOccupation = sponsorOccupation;
	}



	/**
	 * @return the sponsorPhone
	 */
	public String getSponsorPhone() {
		return SponsorPhone;
	}



	/**
	 * @param sponsorPhone the sponsorPhone to set
	 */
	public void setSponsorPhone(String sponsorPhone) {
		SponsorPhone = sponsorPhone;
	}



	/**
	 * @return the sponsorCountry
	 */
	public String getSponsorCountry() {
		return SponsorCountry;
	}



	/**
	 * @param sponsorCountry the sponsorCountry to set
	 */
	public void setSponsorCountry(String sponsorCountry) {
		SponsorCountry = sponsorCountry;
	}



	/**
	 * @return the sponsorCounty
	 */
	public String getSponsorCounty() {
		return SponsorCounty;
	}



	/**
	 * @param sponsorCounty the sponsorCounty to set
	 */
	public void setSponsorCounty(String sponsorCounty) {
		SponsorCounty = sponsorCounty;
	}



	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Student's Sponsor [ getUuid() =");
		builder.append(getUuid());
		builder.append(",studentUuid =");
		builder.append(studentUuid);
		builder.append(",SponsorName =");
		builder.append(SponsorName);
		builder.append(",SponsorOccupation =");
		builder.append(SponsorOccupation);
		builder.append(",SponsorPhone =");
		builder.append(SponsorPhone);
		builder.append(",SponsorCountry =");
		builder.append(SponsorCountry);
		builder.append(",SponsorCounty =");
		builder.append(SponsorCounty);
		builder.append("]");
		return builder.toString(); 
		} 
		

	
}
