

package com.yahoo.petermwenda83.persistence.student;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.StudentSubject;

/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentSubjectDAO {
	
	    public StudentSubject getSubjectById(String accountId,String uuid);
	    
	    public StudentSubject getstudentSubject(String studentId,String subjectId);
	    
		public List<StudentSubject> getStudentSubjects(String studentId);
		
		public boolean putStudentSubject(StudentSubject studentSub);
		
		public boolean deleteAllSubject(String accountId,String studentId); 
		
		public boolean deleteSubject(String accountId,String id); 
		
		
		 

}
