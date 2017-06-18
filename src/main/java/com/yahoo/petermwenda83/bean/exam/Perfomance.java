/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.bean.exam;

import com.yahoo.petermwenda83.bean.StorableBean;

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
	private String term;
	private String year;

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
        term = "";
        year = "";
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
	 * @return the classRoomId
	 */
	public String getClassRoomId() {
		return classRoomId;
	}

	/**
	 * @param classRoomId the classRoomId to set
	 */
	public void setClassRoomId(String classRoomId) {
		this.classRoomId = classRoomId;
	}

	/**
	 * @return the examId
	 */
	public String getExamId() {
		return examId;
	}

	/**
	 * @param examId the examId to set
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
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
	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Perfomance [studentId=" + studentId + ", subjectId=" + subjectId + ", streamId=" + streamId
				+ ", classRoomId=" + classRoomId + ", examId=" + examId + ", score=" + score + ", term=" + term
				+ ", year=" + year + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 5213605489119414121L;
}
