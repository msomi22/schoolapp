/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest.bean;

/**
 * @author peter
 *
 */
public class SubmitExam {
	
	private String accountId;
	private String studentId;
	private String subjectId;
	private String examId;
	private String streamId;
	private int outof;
	private int score;

	
	/**
	 * 
	 */
	public SubmitExam() {
		accountId = "";
		studentId = "";
		subjectId = "";
		examId = "";
		streamId = "";
		outof = 0;
		score = 0;
	}


	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}


	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	 * @return the outof
	 */
	public int getOutof() {
		return outof;
	}


	/**
	 * @param outof the outof to set
	 */
	public void setOutof(int outof) {
		this.outof = outof;
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
		return "SubmitExam [accountId=" + accountId + ", studentId=" + studentId + ", subjectId=" + subjectId
				+ ", examId=" + examId + ", streamId=" + streamId + ", outof=" + outof + ", score=" + score + "]";
	}

}
