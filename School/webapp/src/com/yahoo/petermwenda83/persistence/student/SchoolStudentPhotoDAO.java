/**
 * 
 */
package com.yahoo.petermwenda83.persistence.student;

import com.yahoo.petermwenda83.bean.student.StudentPhoto;

/**
 * @author peter
 *
 */
public interface SchoolStudentPhotoDAO {
	
	public StudentPhoto getPhotoByStudentid(String StudentUuid);

	public StudentPhoto getPhotoByPhotopath(String imagePath);
	
	public boolean putPhoto(StudentPhoto photo);
	
	public boolean updatePhoto(StudentPhoto photo);

}
