/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.util.List;

import com.yahoo.petermwenda83.bean.staff.ClassTeacher;

/**
 * @author peter
 *
 */
public interface SchoolClassTeacherDAO {
	
	/**
	 * 
	 * @param TeacherUuid
	 * @return
	 */
	public ClassTeacher getClassTeacherByteacherId(String TeacherUuid);
	
	/**
	 * 
	 * @param TeacherUuid
	 * @return
	 */
	public ClassTeacher getClassTeacherByclassId(String ClassRoomUuid);
	 /**
	  * 
	  * @param Teacher
	  * @return
	  */
	public boolean putClassTeacher(ClassTeacher Teacher);
	  /**
	   * 
	   * @param Teacher
	   * @return
	   */
	public boolean updateClassTeacher(ClassTeacher Teacher);
	   /**
	    * 
	    * @param Teacher
	    * @return
	    */
	public boolean deleteClassTeacher(ClassTeacher Teacher);
	   /**
	    * 
	    * @return
	    */
	public List<ClassTeacher> getClassTeacherList();

}
