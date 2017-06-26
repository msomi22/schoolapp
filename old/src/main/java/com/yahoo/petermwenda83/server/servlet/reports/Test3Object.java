/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import java.util.ArrayList;
import java.util.List;

import com.yahoo.petermwenda83.bean.exam.Perfomance;

/**
 * @author peter
 *
 */
public class Test3Object {
	
	//List<Perfomance> perfomanceList;
	private List<Perfomance> exam1;
	private List<Perfomance> exam2;
	private List<Perfomance> exam3;
	private String studentId;
	private int totalScore;

	/**
	 * 
	 */
	public Test3Object() {
		exam1 = new ArrayList<>();
		exam2 = new ArrayList<>();
		exam3 = new ArrayList<>();
		studentId = "";
		totalScore = 0;
	}

	
	
	/**
	 * @return the exam1
	 */
	public List<Perfomance> getExam1() {
		return exam1;
	}



	/**
	 * @param exam1 the exam1 to set
	 */
	public void setExam1(List<Perfomance> exam1) {
		this.exam1 = exam1;
	}



	/**
	 * @return the exam2
	 */
	public List<Perfomance> getExam2() {
		return exam2;
	}



	/**
	 * @param exam2 the exam2 to set
	 */
	public void setExam2(List<Perfomance> exam2) {
		this.exam2 = exam2;
	}



	/**
	 * @return the exam3
	 */
	public List<Perfomance> getExam3() {
		return exam3;
	}



	/**
	 * @param exam3 the exam3 to set
	 */
	public void setExam3(List<Perfomance> exam3) {
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
