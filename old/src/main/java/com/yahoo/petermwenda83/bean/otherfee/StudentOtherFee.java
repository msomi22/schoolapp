/**
 * 
 */
package com.yahoo.petermwenda83.bean.otherfee;

import java.sql.Timestamp;
import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/** 
 * @author peter
 *
 */
public class StudentOtherFee extends StorableBean{
	
	
	private String studentId;
	private String otherFeeId;
	private String term; 
	private Timestamp dateAllocated;
	
	/**
	 * 
	 */
	public StudentOtherFee() {
		studentId = "";
		otherFeeId = "";
		term = "";
		dateAllocated = new Timestamp(new Date().getTime());
	}
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getOtherFeeId() {
		return otherFeeId;
	}

	public void setOtherFeeId(String otherFeeId) {
		this.otherFeeId = otherFeeId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Timestamp getDateAllocated() {
		return dateAllocated;
	}

	public void setDateAllocated(Timestamp dateAllocated) {
		this.dateAllocated = dateAllocated;
	}

	@Override
	public String toString() {
		return "StudentOtherFee [studentId=" + studentId + ", otherFeeId=" + otherFeeId + ", term=" + term
				+ ", dateAllocated=" + dateAllocated + "]";
	}

	/**
		 * 
		 */
		private static final long serialVersionUID = -300014716973010890L;
}
