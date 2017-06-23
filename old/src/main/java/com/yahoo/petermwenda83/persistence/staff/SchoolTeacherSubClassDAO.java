/**
 * 
 */
package com.yahoo.petermwenda83.persistence.staff;

import java.util.List;

import com.yahoo.petermwenda83.bean.staff.TeacherSubject;

/**
 * @author peter
 *
 */
public interface SchoolTeacherSubClassDAO {
	/**
	 * 
	 * @param teacherUuid
	 * @return
	 */
	public TeacherSubject getSubjectClass(String teacherUuid);
	
	
	/**
	 * 
	 * @param SubjectUuid
	 * @param ClassRoomUuid
	 * @return
	 */
	
	public TeacherSubject getSubject(String SubjectUuid,String ClassRoomUuid);
	
	
	/**
	 * @param subClass
	 * @return
	 */
	public TeacherSubject getSubjectClass(TeacherSubject subClass);
	
	/**
	 * 
	 * @param teacherUuid
	 * @return
	 */
	public List<TeacherSubject> getSubjectsANDClassesList(String teacherUuid);
	 /**
	  * 
	  * @param subClass
	  * @return
	  */
	public boolean putSubjectClass(TeacherSubject subClass);
	  /**
	   * 
	   * @param subClass
	   * @return
	   */
	public boolean updateSubjectClass(TeacherSubject subClass);
	  /**
	   * 
	   * @param subClass
	   * @return
	   */
	public boolean deleteSubjectClass(TeacherSubject subClass);
	  /**
	   * 
	   * @return
	   */
	public List<TeacherSubject> getSubjectClassList();

}
