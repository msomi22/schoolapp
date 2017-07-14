

package com.yahoo.petermwenda83.persistence.student;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.StudentSubject;

/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentSubjectDAO {
	
	    public StudentSubject studentSubject(String studentId,String subjectId);
	    
		public List<StudentSubject> getStudentSubjects(String studentId);
		
		public boolean putStudentSubject(StudentSubject studentSub);
		
		public boolean deleteStudentSubject(String accountId,String studentId); 
		
		public boolean deleteStudentSubject(String accountId,String studentId,String subjectId); 
		
		
		 

}
