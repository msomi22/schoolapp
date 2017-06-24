

package com.yahoo.petermwenda83.persistence.student;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.StudentPrimary;

/**
 * @author peter
 *
 */
public interface SchoolPrimaryDAO {
	
	public StudentPrimary getStudentPrimary(String accountId,String studentId);
	 
	public boolean putStudentPrimary(StudentPrimary Primary);
	  
	public boolean updateStudentPrimary(StudentPrimary Primary);
	   
	public boolean deleteStudentPrimary(String accountId,String studentId);
	  
	public List<StudentPrimary> getStudentPrimary(String accountId);

}
