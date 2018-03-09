/**
 * Copy Right 2016. FasTech Solutions Ltd.
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
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class YearlyMean extends StorableBean{
	
	private String studentId;
	private String classId;
	private String year;
	private double meanOne;
	private double meanTwo;
	private double meanThree;
	private String termOnePosition;
	private String termTwoPosition;
	private String termThreePosition;
	
	public YearlyMean() {
		studentId = "";
		classId = "";
		year = "";
		meanOne = 0;
		meanTwo = 0;
		meanThree = 0;
		termOnePosition = "";
		termTwoPosition = "";
		termThreePosition = "";
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
	 * @return the classId
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * @param classId the classId to set
	 */
	public void setClassId(String classId) {
		this.classId = classId;
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
	 * @return the meanOne
	 */
	public double getMeanOne() {
		return meanOne;
	}

	/**
	 * @param meanOne the meanOne to set
	 */
	public void setMeanOne(double meanOne) {
		this.meanOne = meanOne;
	}

	/**
	 * @return the meanTwo
	 */
	public double getMeanTwo() {
		return meanTwo;
	}

	/**
	 * @param meanTwo the meanTwo to set
	 */
	public void setMeanTwo(double meanTwo) {
		this.meanTwo = meanTwo;
	}

	/**
	 * @return the meanThree
	 */
	public double getMeanThree() {
		return meanThree;
	}

	/**
	 * @param meanThree the meanThree to set
	 */
	public void setMeanThree(double meanThree) {
		this.meanThree = meanThree;
	}

	/**
	 * @return the termOnePosition
	 */
	public String getTermOnePosition() {
		return termOnePosition;
	}

	/**
	 * @param termOnePosition the termOnePosition to set
	 */
	public void setTermOnePosition(String termOnePosition) {
		this.termOnePosition = termOnePosition;
	}

	/**
	 * @return the termTwoPosition
	 */
	public String getTermTwoPosition() {
		return termTwoPosition;
	}

	/**
	 * @param termTwoPosition the termTwoPosition to set
	 */
	public void setTermTwoPosition(String termTwoPosition) {
		this.termTwoPosition = termTwoPosition;
	}

	/**
	 * @return the termThreePosition
	 */
	public String getTermThreePosition() {
		return termThreePosition;
	}

	/**
	 * @param termThreePosition the termThreePosition to set
	 */
	public void setTermThreePosition(String termThreePosition) {
		this.termThreePosition = termThreePosition;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "YearlyMean [studentId=" + studentId + ", classId=" + classId + ", year=" + year + ", meanOne="
				+ meanOne + ", meanTwo=" + meanTwo + ", meanThree=" + meanThree + ", termOnePosition=" + termOnePosition
				+ ", termTwoPosition=" + termTwoPosition + ", termThreePosition=" + termThreePosition + ", getUuid()="
				+ getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3611433316960505196L;

}
