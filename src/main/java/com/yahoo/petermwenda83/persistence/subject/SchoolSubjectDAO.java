/**
 * SchoolAccount Management System
 * This software belong to Peter Mwenda's and Miwgi Ndungu's Company
 * copywrite peter&MigwiSoftwares.co.ltd
 */
package com.yahoo.petermwenda83.persistence.subject;

import java.util.List;

import com.yahoo.petermwenda83.bean.subject.Subject;



/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolSubjectDAO {
	/**
	 * @param uuid
	 * 
	 */
	public  Subject getSubject(String uuid);
	  /**
	   * 
	   * @param subjectcode
	   * 
	   */
	public  Subject getSubjects(String subjectcode);
	 /**
	  * 
	  * @param subject
	  * 
	  */
	public boolean putSubject(Subject subject);
	
	/**
	 * 
	 * @param subject
	 * 
	 */
	public boolean editSubject(Subject subject,String uuid);
	
	/**
	 * 
	 * @param subject
	 * 
	 */
	public boolean deleteStudent(Subject subject);
	
	/**
	 * 
	 * @return AllSubjects
	 */
	public List<Subject> getAllSubjects();
	
	
	

}
