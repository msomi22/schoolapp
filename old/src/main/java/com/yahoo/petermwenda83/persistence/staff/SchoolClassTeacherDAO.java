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
	 * @param accountId account id 
	 * @param uuid unique id 
	 * @return ClassTeacher object 
	 */
	public ClassTeacher getClassTeacherById(String accountId, String uuid);
	
	/**
	 * 
	 * @param accountId account id 
	 * @param id teacherId OR streamId 
	 * @return ClassTeacher object 
	 */
	public ClassTeacher getClassTeacher(String accountId, String id);
	
	/**
	 * 
	 * @param Teacher {@link ClassTeacher} object
	 * @return whether ClassTeacher object was saved or not
	 */
	public boolean putClassTeacher(ClassTeacher Teacher);
	
	/**
	 * 
	 * @param Teacher {@link ClassTeacher} object
	 * @return whether ClassTeacher object was updated or not
	 */
	public boolean updateClassTeacher(ClassTeacher Teacher);
	
	/**
	 * 
	 * @param accountId account id 
	 * @param uuid unique id 
	 * @return whether ClassTeacher was deleted or not
	 */
	public boolean deleteClassTeacher(String accountId, String uuid);
	
	/**
	 * 
	 * @param accountId account id 
	 * @return {@link List} of {@link ClassTeacher} objects
	 */
	public List<ClassTeacher> getClassTeacherList(String accountId);
	
	/**
	 * 
	 * @param accountId account id 
	 * @param id teacherId OR streamId 
	 * @return {@link List} of {@link ClassTeacher} objects
	 */
	public List<ClassTeacher> getClassTeacherList(String accountId, String id);

}
