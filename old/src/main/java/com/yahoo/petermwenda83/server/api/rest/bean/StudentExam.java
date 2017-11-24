/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class StudentExam implements Comparable<StudentExam>{
	
	private String studentId;
	private int count;
	private String regNo;
	private String firstname;
	private String middlename;
	private String lastname;
	private String score;// (p1,2,3) 

	/**
	 * 
	 */
	public StudentExam() {
		studentId = "";
		count = 0;
		regNo = "";
		firstname = "";
		middlename = "";
		lastname = "";
		score = "";
		
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
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the regNo
	 */
	public String getRegNo() {
		return regNo;
	}

	/**
	 * @param regNo the regNo to set
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the middlename
	 */
	public String getMiddlename() {
		return middlename;
	}

	/**
	 * @param middlename the middlename to set
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentExam [studentId=" + studentId + ", count=" + count + ", regNo=" + regNo + ", firstname="
				+ firstname + ", middlename=" + middlename + ", lastname=" + lastname + ", score=" + score + "]";
	}

	@Override
	public int compareTo(StudentExam ss) {
		return getRegNo().compareTo(((StudentExam) ss).getRegNo());  
		}

	

}
