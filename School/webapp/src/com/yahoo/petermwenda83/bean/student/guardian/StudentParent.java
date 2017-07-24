
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
 * Manages Student's Parent/Relative --During admission
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentParent extends StorableBean  {
	
	/**  
	 * 
	 */
	private static final long serialVersionUID = 5745863584453493132L;
	private String studentUuid;
	private String fathername;
	private String fatherphone;
	private String fatheroccupation;
	private String fatherID;
	private String fatherEmail;
	
	private String mothername;
	private String motherphone;
	private String motheroccupation;
	private String motherID;
	private String motherEmail;
	private String relativeName;
	private String relativePhone;
	
	public StudentParent() {
		super();
		 studentUuid = "";
		 fathername = "";
		 fatherID = "";
		 fatherphone ="";
		 fatheroccupation ="";
		 fatherEmail ="";
		 mothername ="";
		 motherID = "";
		 motherphone = "";
		 motheroccupation = "";
		 motherEmail = "";
		 relativeName = "";
		 relativePhone = "";
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
	 * @return the fathername
	 */
	public String getFathername() {
		return fathername;
	}


	/**
	 * @param fathername the fathername to set
	 */
	public void setFathername(String fathername) {
		this.fathername = fathername;
	}


	/**
	 * @return the fatherphone
	 */
	public String getFatherphone() {
		return fatherphone;
	}


	/**
	 * @param fatherphone the fatherphone to set
	 */
	public void setFatherphone(String fatherphone) {
		this.fatherphone = fatherphone;
	}


	/**
	 * @return the fatheroccupation
	 */
	public String getFatheroccupation() {
		return fatheroccupation;
	}


	/**
	 * @param fatheroccupation the fatheroccupation to set
	 */
	public void setFatheroccupation(String fatheroccupation) {
		this.fatheroccupation = fatheroccupation;
	}


	/**
	 * @return the fatherID
	 */
	public String getFatherID() {
		return fatherID;
	}


	/**
	 * @param fatherID the fatherID to set
	 */
	public void setFatherID(String fatherID) {
		this.fatherID = fatherID;
	}


	/**
	 * @return the fatherEmail
	 */
	public String getFatherEmail() {
		return fatherEmail;
	}


	/**
	 * @param fatherEmail the fatherEmail to set
	 */
	public void setFatherEmail(String fatherEmail) {
		this.fatherEmail = fatherEmail;
	}


	/**
	 * @return the mothername
	 */
	public String getMothername() {
		return mothername;
	}


	/**
	 * @param mothername the mothername to set
	 */
	public void setMothername(String mothername) {
		this.mothername = mothername;
	}


	/**
	 * @return the motherphone
	 */
	public String getMotherphone() {
		return motherphone;
	}


	/**
	 * @param motherphone the motherphone to set
	 */
	public void setMotherphone(String motherphone) {
		this.motherphone = motherphone;
	}


	/**
	 * @return the motheroccupation
	 */
	public String getMotheroccupation() {
		return motheroccupation;
	}


	/**
	 * @param motheroccupation the motheroccupation to set
	 */
	public void setMotheroccupation(String motheroccupation) {
		this.motheroccupation = motheroccupation;
	}


	/**
	 * @return the motherID
	 */
	public String getMotherID() {
		return motherID;
	}


	/**
	 * @param motherID the motherID to set
	 */
	public void setMotherID(String motherID) {
		this.motherID = motherID;
	}


	/**
	 * @return the motherEmail
	 */
	public String getMotherEmail() {
		return motherEmail;
	}


	/**
	 * @param motherEmail the motherEmail to set
	 */
	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}


	/**
	 * @return the relativeName
	 */
	public String getRelativeName() {
		return relativeName;
	}


	/**
	 * @param relativeName the relativeName to set
	 */
	public void setRelativeName(String relativeName) {
		this.relativeName = relativeName;
	}


	/**
	 * @return the relativePhone
	 */
	public String getRelativePhone() {
		return relativePhone;
	}


	/**
	 * @param relativePhone the relativePhone to set
	 */
	public void setRelativePhone(String relativePhone) {
		this.relativePhone = relativePhone;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Student's Parent [ getUuid() =");
		builder.append(getUuid());
		builder.append(", studentUuid = ");
		builder.append(studentUuid);
		builder.append(", fathername = ");
		builder.append(fathername);
		builder.append(", fatherphone =");
		builder.append(fatherphone);
		builder.append(", fatheroccupation =");
		builder.append(fatheroccupation);
		builder.append(", fatherEmail =");
		builder.append(fatherEmail);
		builder.append(", fatherID =");
		builder.append(fatherID);
		builder.append(", mothername = ");
		builder.append(mothername);
		builder.append(", motherphone =");
		builder.append(motherphone);
		builder.append(", motheroccupation =");
		builder.append(motheroccupation);
		builder.append(", motherEmail =");
		builder.append(motherEmail);
		builder.append(", motherID =");
		builder.append(motherID);
		builder.append(", relativeName =");
		builder.append(relativeName);
		builder.append(", relativePhone =");
		builder.append(relativePhone);
		builder.append("]");
		return builder.toString(); 
		}
}
