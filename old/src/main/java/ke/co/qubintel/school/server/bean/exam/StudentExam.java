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
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a> 
 *
 */
public class StudentExam extends StorableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String schoolAccountUuid;
	private String studentUuid;
	private String classRoomUuid;
	private String term;
	private String year;
	

	/**
	 * 
	 */
	public StudentExam() {
		 schoolAccountUuid ="";
		 studentUuid ="";
		 classRoomUuid ="";
		 term ="";
		 year ="";

	}
	

	/**
	 * @return the schoolAccountUuid
	 */
	public String getSchoolAccountUuid() {
		return schoolAccountUuid;
	}


	/**
	 * @param schoolAccountUuid the schoolAccountUuid to set
	 */
	public void setSchoolAccountUuid(String schoolAccountUuid) {
		this.schoolAccountUuid = schoolAccountUuid;
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
	 * @return the classRoomUuid
	 */
	public String getClassRoomUuid() {
		return classRoomUuid;
	}


	/**
	 * @param classRoomUuid the classRoomUuid to set
	 */
	public void setClassRoomUuid(String classRoomUuid) {
		this.classRoomUuid = classRoomUuid;
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


	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Performance, [");
		builder.append("schoolAccountUuid =");
		builder.append(schoolAccountUuid);
		builder.append(",studentUuid=");
		builder.append(studentUuid);
		builder.append(", classRoomUuid =");
		builder.append(classRoomUuid); 
		builder.append(", term =");
		builder.append(term); 
		builder.append(", year =");
		builder.append(year); 
		builder.append("]");
		return builder.toString(); 
		}

}
