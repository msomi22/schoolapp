/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports;

import com.yahoo.petermwenda83.bean.student.Student;

/**
 * @author peter
 *
 */
public class TBIDBean {
	
	private Student student;
	private double mean;
	private double prevMean; 
	private double deviation; 

	/**
	 * 
	 */
	public TBIDBean() {
		student = new Student();
		mean = 0;
		prevMean = 0;
		deviation = 0;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getPrevMean() {
		return prevMean;
	}

	public void setPrevMean(double prevMean) {
		this.prevMean = prevMean;
	}

	public double getDeviation() {
		return deviation;
	}

	public void setDeviation(double deviation) {
		this.deviation = deviation;
	}

	@Override
	public String toString() {
		return "TBIDBean [student=" + student + ", mean=" + mean + ", prevMean=" + prevMean + ", deviation=" + deviation
				+ "]";
	}

}
