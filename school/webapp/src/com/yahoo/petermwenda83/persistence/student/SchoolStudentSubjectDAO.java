

package com.yahoo.petermwenda83.persistence.student;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.StudentSubject;

/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentSubjectDAO {
	
	    /**
	     * 
	     * @param studentSub
	     * @return
	     */
	    public StudentSubject getsubject(String studentuuid,String SubjectUuid);
	    /**
	     * 
	     * @param studentuuid
	     * @return
	     */
		public List<StudentSubject> getstudentSubList(String studentuuid);
		/**
		 * 
		 * @param SubjectUuid
		 * @return
		 */
		public List<StudentSubject> getstudentSubListBySubID(String SubjectUuid);
		 /**
		  * 
		  * @param studentSub
		  * @return
		  */
		public boolean putstudentSub(StudentSubject studentSub);
		 /**
		  * 
		  * @param studentSub
		  * @return
		  */
		public boolean  updatestudentSub(StudentSubject studentSub);
		  /**
		   * 
		   * @param studentSub
		   * @return
		   */
		public boolean deletestudentSub(StudentSubject studentSub);
		 

}
