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
package ke.co.qubintel.school.server.servlet.reports.exam;

/**
 * @author peter
 *
 */
public class PerformanceBean1 {
	
	private String studentId;
	private String subjectId;
	private String streamId;
	private String classRoomId;
	private String houseId;
	private int score;
	private String category;

	/**
	 * 
	 */
	public PerformanceBean1() {
		studentId ="";
        subjectId ="";
        streamId ="";
        classRoomId = "";
        houseId = "";
        score = 0;
        category = "";
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
	 * @return the houseId
	 */
	public String getHouseId() {
		return houseId;
	}

	/**
	 * @param houseId the houseId to set
	 */
	public void setHouseId(String houseId) {
		this.houseId = houseId;
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
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PerformanceBean1 [studentId=" + studentId + ", subjectId=" + subjectId + ", streamId=" + streamId
				+ ", classRoomId=" + classRoomId + ", houseId=" + houseId + ", score=" + score + ", category="
				+ category + "]";
	}

}
