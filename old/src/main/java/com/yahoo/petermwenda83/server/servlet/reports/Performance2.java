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
public class Performance2 {
	
	private Map<String,Integer> exam1;
	private Map<String,Integer> exam2;
	private Map<String,Integer> exam3;
	private String studentId;
	private int totalPoint;
	private int totalMean;
	private int exam1Total;
	private int exam2Total;
	private int exam3Total;
	private String streamId;
	private String classroomId;

	/**
	 * 
	 */
	public Performance2() {
		exam1 = new HashMap<>(); 
		exam2 = new HashMap<>(); 
		exam3 = new HashMap<>(); 
		studentId = "";
		totalPoint = 0;
		totalMean = 0;
		exam1Total = 0;
		exam2Total = 0;
		exam3Total = 0;
		streamId = "";
		classroomId = "";
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
	 * @return the totalPoint
	 */
	public int getTotalPoint() {
		return totalPoint;
	}

	/**
	 * @param totalPoint the totalPoint to set
	 */
	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	/**
	 * @return the totalMean
	 */
	public int getTotalMean() {
		return totalMean;
	}

	/**
	 * @param totalMean the totalMean to set
	 */
	public void setTotalMean(int totalMean) {
		this.totalMean = totalMean;
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
	 * @return the streamId
	 */
	public String getStreamId() {
		return streamId;
	}

	/**
	 * @param streamId the streamId to set
	 */
	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	/**
	 * @return the classroomId
	 */
	public String getClassroomId() {
		return classroomId;
	}

	/**
	 * @param classroomId the classroomId to set
	 */
	public void setClassroomId(String classroomId) {
		this.classroomId = classroomId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Performance2 [exam1=" + exam1 + ", exam2=" + exam2 + ", exam3=" + exam3 + ", studentId=" + studentId
				+ ", totalPoint=" + totalPoint + ", totalMean=" + totalMean + ", exam1Total=" + exam1Total
				+ ", exam2Total=" + exam2Total + ", exam3Total=" + exam3Total + ", streamId=" + streamId
				+ ", classroomId=" + classroomId + "]";
	}

	

}






















