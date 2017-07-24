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

	/**
	 * 
	 */
	private static final long serialVersionUID = 5213605489119414121L;
	private String schoolAccountUuid;
	private String teacherUuid;
	private String studentUuid;
	private String subjectUuid;
	private String classRoomUuid;
	private String ClassesUuid;
	private double catOne;
	private double catTwo;
	private double endTerm;
	private double paperOne;
	private double paperTwo;
	private double paperThree; 
	//private double score; 
	private String term;
	private String year;

	/**
	 * 
	 */
	public Perfomance() {
        super();
        schoolAccountUuid ="";
        teacherUuid ="";
        studentUuid ="";
        subjectUuid ="";
        classRoomUuid = "";
        ClassesUuid = "";
        catOne = 0.0;
        catTwo = 0.0;
        endTerm = 0.0;
        paperOne = 0.0;
        paperTwo = 0.0;
        paperThree = 0.0;
        //totals = 0.0;
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
	 * @return the teacherUuid
	 */
	public String getTeacherUuid() {
		return teacherUuid;
	}




	/**
	 * @param teacherUuid the teacherUuid to set
	 */
	public void setTeacherUuid(String teacherUuid) {
		this.teacherUuid = teacherUuid;
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
	 * @return the subjectUuid
	 */
	public String getSubjectUuid() {
		return subjectUuid;
	}




	/**
	 * @param subjectUuid the subjectUuid to set
	 */
	public void setSubjectUuid(String subjectUuid) {
		this.subjectUuid = subjectUuid;
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
	 * @return the classesUuid
	 */
	public String getClassesUuid() {
		return ClassesUuid;
	}




	/**
	 * @param classesUuid the classesUuid to set
	 */
	public void setClassesUuid(String classesUuid) {
		ClassesUuid = classesUuid;
	}




	/**
	 * @return the catOne
	 */
	public double getCatOne() {
		return catOne;
	}




	/**
	 * @param catOne the catOne to set
	 */
	public void setCatOne(double catOne) {
		this.catOne = catOne;
	}




	/**
	 * @return the catTwo
	 */
	public double getCatTwo() {
		return catTwo;
	}




	/**
	 * @param catTwo the catTwo to set
	 */
	public void setCatTwo(double catTwo) {
		this.catTwo = catTwo;
	}




	/**
	 * @return the endTerm
	 */
	public double getEndTerm() {
		return endTerm;
	}




	/**
	 * @param endTerm the endTerm to set
	 */
	public void setEndTerm(double endTerm) {
		this.endTerm = endTerm;
	}




	/**
	 * @return the paperOne
	 */
	public double getPaperOne() {
		return paperOne;
	}




	/**
	 * @param paperOne the paperOne to set
	 */
	public void setPaperOne(double paperOne) {
		this.paperOne = paperOne;
	}




	/**
	 * @return the paperTwo
	 */
	public double getPaperTwo() {
		return paperTwo;
	}




	/**
	 * @param paperTwo the paperTwo to set
	 */
	public void setPaperTwo(double paperTwo) {
		this.paperTwo = paperTwo;
	}




	/**
	 * @return the paperThree
	 */
	public double getPaperThree() {
		return paperThree;
	}




	/**
	 * @param paperThree the paperThree to set
	 */
	public void setPaperThree(double paperThree) {
		this.paperThree = paperThree;
	}




	/**
	 * @return the totals
	 */
	public double getTotals() {
		return (  getCatOne()+getCatTwo()  )/2   + getEndTerm();
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
		builder.append(",teacherUuid=");
		builder.append(teacherUuid);
		builder.append(", studentUuid =");
		builder.append(studentUuid); 
		builder.append(", subjectUuid =");
		builder.append(subjectUuid); 
		builder.append(", classRoomUuid =");
		builder.append(classRoomUuid);
		builder.append(", ClassesUuid =");
		builder.append(ClassesUuid);
		builder.append(", catOne =");
		builder.append(catOne);
		builder.append(", catTwo =");
		builder.append(catTwo);
		builder.append(", endTerm =");
		builder.append(endTerm);
		builder.append(", paperOne =");
		builder.append(paperOne);
		builder.append(", paperTwo =");
		builder.append(paperTwo);
		builder.append(", paperThree =");
		builder.append(paperThree);
		builder.append(", term =");
		builder.append(term);
		builder.append(", year =");
		builder.append(year);
		builder.append("]");
		return builder.toString(); 
		}
	

}
