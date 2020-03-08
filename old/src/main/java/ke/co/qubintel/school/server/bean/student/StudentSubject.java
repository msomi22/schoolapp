
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
package ke.co.qubintel.school.server.bean.student;

import java.sql.Timestamp;
import java.util.Date;

import ke.co.qubintel.school.server.bean.StorableBean;

/** 
 * Student's Subject-Class Allocation 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 * 
 */
public class StudentSubject extends StorableBean {
	
	private String studentId;
	private String subjectId;
	private Timestamp allocationDate;
	
	public StudentSubject(){
		   super();
		   studentId = "";
		   subjectId = "";
		   allocationDate = new Timestamp(new Date().getTime());
	   }

	

	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return studentId;
	}



	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}



	/**
	 * @return the subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}



	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}



	/**
	 * @return the allocationDate
	 */
	public Timestamp getAllocationDate() {
		return allocationDate;
	}



	/**
	 * @param allocationDate the allocationDate to set
	 */
	public void setAllocationDate(Timestamp allocationDate) {
		this.allocationDate = allocationDate;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentSubject [studentId=" + studentId + ", subjectId=" + subjectId + ", allocationDate="
				+ allocationDate + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}



	private static final long serialVersionUID = 1L;
}
