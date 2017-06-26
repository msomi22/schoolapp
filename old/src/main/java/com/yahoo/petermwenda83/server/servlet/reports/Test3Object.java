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

	/**
	 * 
	 */
	public Test3Object() {
		exam1 = new HashMap<>(); 
		exam2 = new HashMap<>(); 
		exam3 = new HashMap<>(); 
		studentId = "";
		totalScore = 0;
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

	@Override
	public String toString() {
		return "Test3Object [exam1=" + exam1 + ", exam2=" + exam2 + ", exam3=" + exam3 + ", studentId=" + studentId
				+ ", totalScore=" + totalScore + "]";
	}

	

}
