/**
 * 
 */
package com.yahoo.petermwenda83.bean.exam;

import com.yahoo.petermwenda83.bean.StorableBean;

/**
 * @author peter
 *
 */
public class Deviation extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4304459493382193867L;
	private String studentUuid;
	private String year;
	private double devOne;
	private double devTwo;
	private double devThree;

	/**
	 * 
	 */
	public Deviation() {
		studentUuid = "";
		year = "";
		devOne = 0;
		devTwo = 0;
		devThree = 0;
	}
	
	/**
	 * @return the studentUuid
	 */
	public String getStudentUuid() {
		return studentUuid;
	}

	/**
	 * @param studentUuid the studentUuid to set
	 */
	public void setStudentUuid(String studentUuid) {
		this.studentUuid = studentUuid;
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
	 * @return the devOne
	 */
	public double getDevOne() {
		return devOne;
	}

	/**
	 * @param devOne the devOne to set
	 */
	public void setDevOne(double devOne) {
		this.devOne = devOne;
	}

	/**
	 * @return the devTwo
	 */
	public double getDevTwo() {
		return devTwo;
	}

	/**
	 * @param devTwo the devTwo to set
	 */
	public void setDevTwo(double devTwo) {
		this.devTwo = devTwo;
	}

	/**
	 * @return the devThree
	 */
	public double getDevThree() {
		return devThree;
	}

	/**
	 * @param devThree the devThree to set
	 */
	public void setDevThree(double devThree) {
		this.devThree = devThree;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Deviation [ getUuid() = ");
		builder.append(getUuid());
		builder.append(", studentUuid=");
		builder.append(studentUuid);
		builder.append(", year=");
		builder.append(year);
		builder.append(", devOne=");
		builder.append(devOne);
		builder.append(", devTwo=");
		builder.append(devTwo);
		builder.append(", devThree=");
		builder.append(devThree);
		builder.append("]");
		return builder.toString(); 
		}

}
