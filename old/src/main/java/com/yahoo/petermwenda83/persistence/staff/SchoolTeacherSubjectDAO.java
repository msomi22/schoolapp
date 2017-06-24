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
public interface SchoolTeacherSubjectDAO {
	
	public TeacherSubject getTeacherSubject(String accountId, String streamId, String subjectId);
	
	public boolean putTeacherSubject(TeacherSubject teacherSubject);
	
	public boolean deleteTeacherSubject(String accountId, String teacherId, String streamId, String subjectId);
	
	public List<TeacherSubject> getTeacherSubjects(String accountId, String teacherId);
	
	

}
