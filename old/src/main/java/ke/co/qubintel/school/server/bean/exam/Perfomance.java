/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */

package ke.co.qubintel.school.server.bean.exam;

import ke.co.qubintel.school.server.bean.StorableBean;

/** 
 * Student performance in a school
 * 
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class Perfomance extends StorableBean{

	private String studentId;
	private String subjectId;
	private String streamId;
	private String classRoomId;
	private String examId;
	private int score;
	private int paper1; 
	private int paper2;
	private int paper3; 
	private String term;
	private String year;//

	/**
	 * 
	 */
	public Perfomance() {
        super();
        studentId ="";
        subjectId ="";
        streamId ="";
        classRoomId = "";
        examId = "";
        score = 0;
        paper1 = 0;
        paper2 = 0;
        paper3 = 0;
        term = "";
        year = "";
	}
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	public String getClassRoomId() {
		return classRoomId;
	}

	public void setClassRoomId(String classRoomId) {
		this.classRoomId = classRoomId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPaper1() {
		return paper1;
	}

	public void setPaper1(int paper1) {
		this.paper1 = paper1;
	}

	public int getPaper2() {
		return paper2;
	}

	public void setPaper2(int paper2) {
		this.paper2 = paper2;
	}

	public int getPaper3() {
		return paper3;
	}

	public void setPaper3(int paper3) {
		this.paper3 = paper3;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Perfomance [studentId=" + studentId + ", subjectId=" + subjectId + ", streamId=" + streamId
				+ ", classRoomId=" + classRoomId + ", examId=" + examId + ", score=" + score + ", paper1=" + paper1
				+ ", paper2=" + paper2 + ", paper3=" + paper3 + ", term=" + term + ", year=" + year + ", getUuid()="
				+ getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5213605489119414121L;
}
