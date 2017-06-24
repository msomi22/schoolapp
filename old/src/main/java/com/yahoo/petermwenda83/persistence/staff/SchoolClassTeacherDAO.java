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
	
	public ClassTeacher getClassTeacher(String accountId, String streamId);
	
	public boolean putClassTeacher(ClassTeacher Teacher);
	
	public boolean deleteClassTeacher(String accountId, String uuid);
	
	public List<ClassTeacher> getClassTeacherList(String accountId);

}
