/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.util.HashMap;
import java.util.Map;

/**
 * @author peter
 *
 */
public class Test3Object {
	
	private Map<String,Integer> exam1;
	private Map<String,Integer> exam2;
	private Map<String,Integer> exam3;
	private String studentId;
	private int totalScore;
	private int exam1Total;
	private int exam2Total;
	private int exam3Total;

	/**
	 * 
	 */
	public Test3Object() {
		exam1 = new HashMap<>(); 
		exam2 = new HashMap<>(); 
		exam3 = new HashMap<>(); 
		studentId = "";
		totalScore = 0;
		exam1Total = 0;
		exam2Total = 0;
		exam3Total = 0;
	}

	/**
	 * @return the exam1
	 */
	public Map<String, Integer> getExam1() {
		return exam1;
	}

	/**
	 * @param exam1 the exam1 to set
	 */
	public void setExam1(Map<String, Integer> exam1) {
		this.exam1 = exam1;
	}

	/**
	 * @return the exam2
	 */
	public Map<String, Integer> getExam2() {
		return exam2;
	}

	/**
	 * @param exam2 the exam2 to set
	 */
	public void setExam2(Map<String, Integer> exam2) {
		this.exam2 = exam2;
	}

	/**
	 * @return the exam3
	 */
	public Map<String, Integer> getExam3() {
		return exam3;
	}

	/**
	 * @param exam3 the exam3 to set
	 */
	public void setExam3(Map<String, Integer> exam3) {
		this.exam3 = exam3;
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
	 * @return the totalScore
	 */
	public int getTotalScore() {
		return totalScore;
	}

	/**
	 * @param totalScore the totalScore to set
	 */
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	

	/**
	 * @return the exam1Total
	 */
	public int getExam1Total() {
		return exam1Total;
	}

	/**
	 * @param exam1Total the exam1Total to set
	 */
	public void setExam1Total(int exam1Total) {
		this.exam1Total = exam1Total;
	}

	/**
	 * @return the exam2Total
	 */
	public int getExam2Total() {
		return exam2Total;
	}

	/**
	 * @param exam2Total the exam2Total to set
	 */
	public void setExam2Total(int exam2Total) {
		this.exam2Total = exam2Total;
	}

	/**
	 * @return the exam3Total
	 */
	public int getExam3Total() {
		return exam3Total;
	}

	/**
	 * @param exam3Total the exam3Total to set
	 */
	public void setExam3Total(int exam3Total) {
		this.exam3Total = exam3Total;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Test3Object [exam1=" + exam1 + ", exam2=" + exam2 + ", exam3=" + exam3 + ", studentId=" + studentId
				+ ", totalScore=" + totalScore + ", exam1Total=" + exam1Total + ", exam2Total=" + exam2Total
				+ ", exam3Total=" + exam3Total + "]";
	}

	

}
