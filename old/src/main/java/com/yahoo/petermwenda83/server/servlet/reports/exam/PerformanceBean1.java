/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.exam;

/**
 * @author peter
 *
 */
public class PerformanceBean1 {
	
	private String studentId;
	private String subjectId;
	private String streamId;
	private String classRoomId;
	private int score;

	/**
	 * 
	 */
	public PerformanceBean1() {
		studentId ="";
        subjectId ="";
        streamId ="";
        classRoomId = "";
        score = 0;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PerformanceBean1 [studentId=" + studentId + ", subjectId=" + subjectId + ", streamId=" + streamId
				+ ", classRoomId=" + classRoomId + ", score=" + score + "]";
	}


	

}
