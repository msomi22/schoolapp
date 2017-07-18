/**
 * 
 */
package com.yahoo.petermwenda83.bean.othermoney;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class StudentOtherMonies extends StorableBean{
	
	
	   String studentUuid;
	   String otherstypeUuid;
	   double amountPiad;
	   String term;
	   String year;


	/**
	 * 
	 */
	public StudentOtherMonies() {
		   studentUuid = "";
		   otherstypeUuid = "";
		   amountPiad =0;
		   term = "";
		   year = "";
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
	 * @return the otherstypeUuid
	 */
	public String getOtherstypeUuid() {
		return otherstypeUuid;
	}


	/**
	 * @param otherstypeUuid the otherstypeUuid to set
	 */
	public void setOtherstypeUuid(String otherstypeUuid) {
		this.otherstypeUuid = otherstypeUuid;
	}


	/**
	 * @return the amountPiad
	 */
	public double getAmountPiad() {
		return amountPiad;
	}


	/**
	 * @param amountPiad the amountPiad to set
	 */
	public void setAmountPiad(double amountPiad) {
		this.amountPiad = amountPiad;
	}


	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}


	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}


	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}


	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Student Other Money");
		builder.append(",[getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid =");
		builder.append(studentUuid); 
		builder.append(", otherstypeUuid =");
		builder.append(otherstypeUuid); 
		builder.append(", amountPiad =");
		builder.append(amountPiad); 
		builder.append(", term =");
		builder.append(term); 
		builder.append(", year =");
		builder.append(year); 
		builder.append("]");
		return builder.toString(); 
		}

	
	   /**
		 * 
		 */
		private static final long serialVersionUID = -300014716973010890L;
}
