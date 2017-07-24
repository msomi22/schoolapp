/**
 * 
 */
package com.yahoo.petermwenda83.bean.money;

import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class StudentFee extends StorableBean{
	
	/** 
	 * 
	 */
	private static final long serialVersionUID = -4526710616012197088L;
		private String schoolAccountUuid ;
	    private String studentUuid;
	    private String transactionID;
	    private double amountPaid ;
	    private double amountTokenizer;
	    private Date DatePaid;
	    private String Term;
	    private String Year;
	    private String studentType;
	    private String SystemUser;
	
	
	public StudentFee() {
		schoolAccountUuid = "";
		studentUuid = "";
		transactionID = "";
		amountPaid = 0.0;
		amountTokenizer = 0.0;
		DatePaid = new Date();
		Term = "";
		Year = "";
		studentType = "";
		SystemUser = "";
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
	 * @return the transactionID
	 */
	public String getTransactionID() {
		return transactionID;
	}


	/**
	 * @param transactionID the transactionID to set
	 */
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}


	/**
	 * @return the amountPaid
	 */
	public double getAmountPaid() {
		return amountPaid;
	}


	/**
	 * @param amountPaid the amountPaid to set
	 */
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
    

	/**
	 * @return the amountTokenizer
	 */
	public double getAmountTokenizer() {
		return amountTokenizer;
	}


	/**
	 * @param amountTokenizer the amountTokenizer to set
	 */
	public void setAmountTokenizer(double amountTokenizer) {
		this.amountTokenizer = amountTokenizer;
	}


	/**
	 * @return the datePaid
	 */
	public Date getDatePaid() {
		return DatePaid;
	}


	/**
	 * @param datePaid the datePaid to set
	 */
	public void setDatePaid(Date datePaid) {
		DatePaid = datePaid;
	}
   

	/**
	 * @return the term
	 */
	public String getTerm() {
		return Term;
	}


	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		Term = term;
	}


	/**
	 * @return the year
	 */
	public String getYear() {
		return Year;
	}


	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		Year = year;
	}


	/**
	 * @return the studentType
	 */
	public String getStudentType() {
		return studentType;
	}


	/**
	 * @param studentType the studentType to set
	 */
	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}


	/**
	 * @return the systemUser
	 */
	public String getSystemUser() {
		return SystemUser;
	}


	/**
	 * @param systemUser the systemUser to set
	 */
	public void setSystemUser(String systemUser) {
		SystemUser = systemUser;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Student Fee");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(", schoolAccountUuid =");
		builder.append(schoolAccountUuid);
		builder.append(", studentUuid =");
		builder.append(studentUuid);
		builder.append(", transactionID =");
		builder.append(transactionID);
		builder.append(", amountPaid =");
		builder.append(amountPaid);
		builder.append(", amountTokenizer =");
		builder.append(amountTokenizer);
		builder.append(", DatePaid =");
		builder.append(DatePaid);
		builder.append(", Term =");
		builder.append(Term);
		builder.append(", Year =");
		builder.append(Year);
		builder.append(", studentType =");
		builder.append(studentType);
		builder.append(", SystemUser =");
		builder.append(SystemUser);
		builder.append("]");
		return builder.toString(); 
		}

}
