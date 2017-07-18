

package com.yahoo.petermwenda83.persistence.student;
import java.util.List;

import com.yahoo.petermwenda83.bean.student.Student;



/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolStudentDAO {

	public Student getStudentById(String accountId,String uuid);

	public int getNextregNo(String accountId); 

	public Student getStudentByregNo(String accountId,String regNo);

	/**
	 * 
	 * @param schoolaccount
	 * @param admno
	 * @return	a {@link List} of {@link Student}s whose query partly or wholly
	 * matches the query and belongs to a particular school account. Matching is case 
	 * insensitive. An empty list is returned if no Student matches the query.
	 */
	public List<Student> searchStudent(String accountId, String query);

	public boolean putStudent(Student student);

	public boolean updateStudent(Student student);

	public boolean deleteStudent(String accountId,String uuid);

	public List<Student> getStudentByStream(String accountId,String currentStream); 

	public List<Student> getAllStudent(String accountId, int startIndex , int endIndex);
	
	
	
	public int classStudentCount(String accountId,String currentStream, String isActive);

	public int activeCount(String accountId, String isActive);
	
	public int alumniCount(String accountId, String isAlumni);
	
	public int dayCount(String accountId, String isActive, String isBoarding);
	
	public int genderCount(String accountId, String isActive, String gender);






}
