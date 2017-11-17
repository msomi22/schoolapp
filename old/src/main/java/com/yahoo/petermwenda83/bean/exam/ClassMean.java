/**
 * 
 */
package com.yahoo.petermwenda83.bean.exam;

import java.sql.Timestamp;
import java.util.Date;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter  
 *
 */
public class ClassMean extends StorableBean{
	
	private String classId;
	private String streamId;
	private String examId;
	private double classmean;
	private double streammean;
	private String term;
	private String year;
	private Timestamp dateAdded;

	/**
	 * 
	 */
	public ClassMean() {
		classId = "";
		streamId = "";
		examId = "";
		classmean = 0;
		streammean = 0;
		term = "";
		year = "";
		dateAdded = new Timestamp(new Date().getTime()); 
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public double getClassmean() {
		return classmean;
	}

	public void setClassmean(double classmean) {
		this.classmean = classmean;
	}

	public double getStreammean() {
		return streammean;
	}

	public void setStreammean(double streammean) {
		this.streammean = streammean;
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

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	
	
	@Override
	public String toString() {
		return "ClassMean [classId=" + classId + ", streamId=" + streamId + ", examId=" + examId + ", classmean="
				+ classmean + ", streammean=" + streammean + ", term=" + term + ", year=" + year + ", dateAdded="
				+ dateAdded + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -3236040348162633825L;

	
}
