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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.classroom.Stream;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Subject;

/**
 * Student performance in a school
 * 
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
@Entity
@Table( name = "perfomance" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Perfomance extends StorableBeanByUUID{
	
	private int score;
	private String term;
	private String year;
	
	@ManyToOne
	@JoinColumn(name="accountId", referencedColumnName="uuid")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="studentId", referencedColumnName="uuid")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="subjectId", referencedColumnName="uuid")
	private Subject subject;
	
	@ManyToOne
	@JoinColumn(name="classRoomId", referencedColumnName="uuid")
	private ClassRoom classRoom;
	
	@ManyToOne
	@JoinColumn(name="streamId", referencedColumnName="uuid")
	private Stream stream;
	
	@ManyToOne
	@JoinColumn(name="examId", referencedColumnName="uuid")
	private Exam exam;

	/**
	 * 
	 */
	public Perfomance() {
        super();
        score = 0;
        term = "";
        year = "";
        
        account = new Account();
		student = new Student();
		subject = new Subject();
		classRoom = new ClassRoom();
		stream = new Stream();
		exam = new Exam();
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
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}


	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}


	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}


	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}


	/**
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}


	/**
	 * @param subject the subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	/**
	 * @return the classRoom
	 */
	public ClassRoom getClassRoom() {
		return classRoom;
	}


	/**
	 * @param classRoom the classRoom to set
	 */
	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}


	/**
	 * @return the stream
	 */
	public Stream getStream() {
		return stream;
	}


	/**
	 * @param stream the stream to set
	 */
	public void setStream(Stream stream) {
		this.stream = stream;
	}


	/**
	 * @return the exam
	 */
	public Exam getExam() {
		return exam;
	}


	/**
	 * @param exam the exam to set
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Perfomance [score=" + score + ", term=" + term + ", year=" + year + ", account=" + account
				+ ", student=" + student + ", subject=" + subject + ", classRoom=" + classRoom + ", stream=" + stream
				+ ", exam=" + exam + ", getUuid()=" + getUuid() + "]";
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 5213605489119414121L;
}
