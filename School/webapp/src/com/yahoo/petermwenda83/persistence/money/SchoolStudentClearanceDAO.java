/**
 * 
 */
package com.yahoo.petermwenda83.persistence.money;

import com.yahoo.petermwenda83.bean.money.StudentClearance;

/**
 *  Used for clearing student from the system 
 *  
 * @author peter
 *
 */
public interface SchoolStudentClearanceDAO {
	/**
	 * 
	 * @param studentUuid
	 * @param term
	 * @param year
	 * @return student object
	 */
	public StudentClearance getClearance(String studentUuid,String term,String year);
	 /**
	  * 
	  * @param studentUuid
	  * @param year
	  * @return student object
	  */
	public StudentClearance getClearanceByYear(String studentUuid,String year);
	 /**
	  * 
	  * @param studentUuid
	  * @return student object
	  */
	public StudentClearance getClearanceByStudentId(String studentUuid);
	 /**
	  * 
	  */
	public boolean existStudent(String studentUuid,String term,String year);
	 /**
	  * 
	  * @param clearance
	  * @return whether the student has been added successfully or not
	  */
	public boolean put(StudentClearance clearance,String studentUuid, String term, String year);

}
